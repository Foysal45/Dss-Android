package com.dss.hrms.view.payroll.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide.init
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogPayrollManagementLayoutBinding
import com.dss.hrms.databinding.FragmentEmployeeBankInformationBinding
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.error.ApiError
import com.dss.hrms.model.error.ErrorUtils2
import com.dss.hrms.util.*
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.allInterface.OnDateListener
import com.dss.hrms.view.leave.adapter.spinner.LeavePolicySpinnerAdapter
import com.dss.hrms.view.leave.model.LeaveApplicationApiResponse
import com.dss.hrms.view.payroll.`interface`.OnPayRollInfoClickListener
import com.dss.hrms.view.payroll.adapter.BankInformationAdapter
import com.dss.hrms.view.payroll.model.PayRollBankInfo
import com.dss.hrms.view.payroll.viewmodel.PayRollBankInformationViewModel
import com.dss.hrms.view.personalinfo.adapter.SpinnerAdapter
import com.dss.hrms.viewmodel.CommonViewModel
import com.dss.hrms.viewmodel.UtilViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.example.dagger_android_practical.di.viewmodel.ViewModelFactoryModule
import dagger.android.support.DaggerFragment
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class EmployeeBankInformationFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData

    var employee: Employee? = null

    lateinit var binding: FragmentEmployeeBankInformationBinding
    lateinit var dataList: List<PayRollBankInfo?>
    lateinit var layoutManager: LinearLayoutManager
    lateinit var payrollBankInformationViewmodel: PayRollBankInformationViewModel

    lateinit var adapter: BankInformationAdapter

    lateinit var dialogCustome: Dialog
    lateinit var bankInformationBinding: DialogPayrollManagementLayoutBinding

    lateinit var utilViewModel: UtilViewModel
    lateinit var commonViewModel: CommonViewModel


    var district: SpinnerDataModel? = null
    var accountType: SpinnerDataModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEmployeeBankInformationBinding.inflate(inflater, container, false)
        employee = employeeProfileData.employee
        init()
        payrollBankInformationViewmodel.apply {
            var loadingDialog = CustomLoadingDialog().createLoadingDialog(activity)
            getBankInfo(employee?.user?.employee_id)

            bankInfo.observe(viewLifecycleOwner, androidx.lifecycle.Observer { data ->
                loadingDialog?.dismiss()
                data?.let {
                    dataList = it
                    prepareRecycleView()

                }
            })
        }


        binding.fab.setOnClickListener {
            showEditCreateDialog(Operation.CREATE, null)
        }

        return binding.root
    }


    fun prepareRecycleView() {
        layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
        adapter = BankInformationAdapter()
        activity?.let {
            adapter.setInitialData(dataList, it, object : OnPayRollInfoClickListener {
                override fun onClick(any: Any?, position: Int, operation: Operation) {
                    showEditCreateDialog(Operation.EDIT, any as PayRollBankInfo)
                }

            })
        }

        binding.recyclerView.adapter = adapter

    }


    private fun init() {
        payrollBankInformationViewmodel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(PayRollBankInformationViewModel::class.java)

        utilViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(UtilViewModel::class.java)

        commonViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(CommonViewModel::class.java)
    }


    fun showEditCreateDialog(
        operation: Operation,
        payrollBankInfo: PayRollBankInfo?
    ) {

        dialogCustome = activity?.let { Dialog(it) }!!
        dialogCustome?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        bankInformationBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_payroll_management_layout,
            null,
            false
        )
        bankInformationBinding?.getRoot()?.let { dialogCustome?.setContentView(it) }
        var window: Window? = dialogCustome?.getWindow()
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        bankInformationBinding.llBankInformation.visibility = View.VISIBLE

        bankInformationBinding.BIAccountNumber.etText.inputType = InputType.TYPE_CLASS_PHONE

        bankInformationBinding.BIBankName.etText.setText(payrollBankInfo?.bank_name)
        bankInformationBinding.BIBranchName.etText.setText(payrollBankInfo?.branch_name)
        bankInformationBinding.BIAccountNumber.etText.setText(payrollBankInfo?.account_no)
        bankInformationBinding.BIAccountName.etText.setText(payrollBankInfo?.account_name)
        bankInformationBinding.BIBankRoutingNumber.etText.setText(payrollBankInfo?.routing_no)


        if (operation == Operation.CREATE) {
            bankInformationBinding.bankAccountBtnUpdate.text = getString(R.string.create)
        } else {
            bankInformationBinding.bankAccountBtnUpdate.text = getString(R.string.update)
        }


        commonViewModel.getAllDistrict()
            ?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { list ->
                list?.let {
                    SpinnerAdapter().setSpinner(
                        bankInformationBinding.BIDistrict?.spinner!!,
                        context,
                        list,
                        payrollBankInfo?.district_id,
                        object : CommonSpinnerSelectedItemListener {
                            override fun selectedItem(any: Any?) {
                                district = any as SpinnerDataModel
                            }
                        }
                    )
                }
            })
        commonViewModel.getCommonData("/api/auth/account-type/list")
            ?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { list ->
                list?.let {
                    SpinnerAdapter().setSpinner(
                        bankInformationBinding?.BIAccountType?.spinner!!,
                        context,
                        list,
                        payrollBankInfo?.account_type_id?.toInt(),
                        object : CommonSpinnerSelectedItemListener {
                            override fun selectedItem(any: Any?) {
                                accountType = any as SpinnerDataModel
                            }

                        }
                    )
                }
            })


        bankInformationBinding.hBankInformation.tvClose.setOnClickListener {
            dialogCustome?.dismiss()
        }
        bankInformationBinding.bankAccountBtnUpdate.setOnClickListener {
            var loadingDialog = CustomLoadingDialog().createLoadingDialog(activity)
            if (operation == Operation.CREATE) {

                payrollBankInformationViewmodel.createBankinformation(getMapData())
                    .observe(viewLifecycleOwner,
                        androidx.lifecycle.Observer {
                            loadingDialog?.dismiss()
                            showResponse(it)

                        })

            } else {
                payrollBankInformationViewmodel.updateBankinformation(
                    getMapData(),
                    payrollBankInfo?.id
                )
                    .observe(viewLifecycleOwner,
                        androidx.lifecycle.Observer {
                            loadingDialog?.dismiss()
                            showResponse(it)

                        })

            }
        }
        dialogCustome?.show()
    }


    fun showResponse(any: Any?) {
        if (any is String) {
            toast(activity, any)
            payrollBankInformationViewmodel. getBankInfo(employee?.user?.employee_id)
            dialogCustome?.dismiss()

        } else if (any is ApiError) {
            try {
                if (any.getError().isEmpty()) {
                    toast(activity, any.getMessage())
                    Log.d("ok", "error")
                } else {
                    for (n in any.getError().indices) {
                        val error = any.getError()[n].getField()
                        val message = any.getError()[n].getMessage()
                        if (TextUtils.isEmpty(error)) {
                            message?.let {
                                bankInformationBinding?.BIAccountType?.llBody.visibility =
                                    View.VISIBLE
                                bankInformationBinding?.BIAccountType?.tvError.text =
                                    ErrorUtils2.mainError(message)
                            }
                        }
                        Log.e("ok", "error ${ErrorUtils2.mainError(message)}")
                        when (error) {
                            "bank_name" -> {
                                bankInformationBinding?.BIBankName?.tvError?.visibility =
                                    View.VISIBLE
                                bankInformationBinding?.BIBankName?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "branch_name" -> {
                                bankInformationBinding?.BIBranchName?.tvError?.visibility =
                                    View.VISIBLE
                                bankInformationBinding?.BIBranchName?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "district_id" -> {
                                bankInformationBinding?.BIDistrict?.tvError?.visibility =
                                    View.VISIBLE
                                bankInformationBinding?.BIDistrict?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "account_no" -> {
                                bankInformationBinding?.BIAccountNumber?.tvError?.visibility =
                                    View.VISIBLE
                                bankInformationBinding?.BIAccountNumber?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "account_name" -> {
                                bankInformationBinding?.BIAccountName?.tvError?.visibility =
                                    View.VISIBLE
                                bankInformationBinding?.BIAccountName?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "routing_no" -> {
                                bankInformationBinding?.BIBankRoutingNumber?.tvError?.visibility =
                                    View.VISIBLE
                                bankInformationBinding?.BIBankRoutingNumber?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "account_type_id" -> {
                                bankInformationBinding?.BIAccountType?.tvError?.visibility =
                                    View.VISIBLE
                                bankInformationBinding?.BIAccountType?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                        }
                    }
                }
            } catch (e: Exception) {
                toast(activity, e.toString())
            }

        } else if (any is Throwable) {
            toast(activity, any.toString())
        } else {
            activity?.getString(R.string.failed)?.let {
                toast(
                    activity,
                    it
                )
            }!!
        }
    }


    fun getMapData(): HashMap<Any, Any?> {
        var map = HashMap<Any, Any?>()
        map.put("employee_id", employee?.user?.employee_id.toString())
        map.put(
            "bank_name",
            bankInformationBinding.BIBankName.etText.text.trim().toString()
        )
        map.put("branch_name", bankInformationBinding.BIBranchName.etText.text.trim().toString())
        map.put(
            "district_id", district?.id
        )
        map.put("account_no", bankInformationBinding.BIAccountNumber.etText.text.trim().toString())
        map.put("account_name", bankInformationBinding.BIAccountName.etText.text.trim().toString())
        map.put(
            "routing_no",
            bankInformationBinding.BIBankRoutingNumber.etText.text.trim().toString()
        )
        map.put("account_type_id", accountType?.id.toString())

        return map
    }


    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }

    fun invisiableAllError() {
        bankInformationBinding?.BIBankName?.llBody.visibility =
            View.GONE
        bankInformationBinding?.BIBranchName?.llBody.visibility =
            View.GONE
        bankInformationBinding?.BIAccountNumber?.llBody.visibility =
            View.GONE
        bankInformationBinding?.BIAccountName?.llBody.visibility =
            View.GONE
        bankInformationBinding?.BIDistrict?.llBody.visibility =
            View.GONE
        bankInformationBinding?.BIBankRoutingNumber?.llBody.visibility =
            View.GONE
        bankInformationBinding?.BIAccountType?.llBody.visibility =
            View.GONE

    }

}