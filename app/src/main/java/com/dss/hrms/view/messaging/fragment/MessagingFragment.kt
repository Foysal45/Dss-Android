package com.dss.hrms.view.messaging.fragment

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.dss.hrms.R
import com.dss.hrms.databinding.FragmentMessageBinding
import com.dss.hrms.model.Office
import com.dss.hrms.model.RoleWiseEmployeeResponseClass
import com.dss.hrms.model.error.ApiError
import com.dss.hrms.model.error.ErrorUtils2
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.view.personalinfo.adapter.SpinnerAdapter
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.allInterface.OfficeDataValueListener
import com.dss.hrms.view.dialog.OfficeSearchingDialog
import com.dss.hrms.view.messaging.viewmodel.MessagingViewModel
import com.dss.hrms.viewmodel.UtilViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import dagger.android.support.DaggerFragment
import java.util.HashMap
import javax.inject.Inject


class MessagingFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var preparence: MySharedPreparence

    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var officeSearchingDialog: OfficeSearchingDialog

    lateinit var utilViewmodel: UtilViewModel

    lateinit var messageViewModel: MessagingViewModel
    var office: Office? = null


    var selectedDataList = arrayListOf<RoleWiseEmployeeResponseClass.RoleWiseEmployee>()

    var officeList = arrayListOf<Office>()
    var mainOfficeList: List<Office>? = null

    lateinit var binding: FragmentMessageBinding

    var isAlreadyViewCreated = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!isAlreadyViewCreated) {
            isAlreadyViewCreated = true
            binding = FragmentMessageBinding.inflate(inflater, container, false)
            init()

            binding.ivSearch.setOnClickListener {
                officeSearchingDialog.showOfficeSearchDialog(
                    activity,
                    utilViewmodel,
                    object : OfficeDataValueListener {
                        override fun valueChange(officeList: List<Office>?) {
                            mainOfficeList = officeList
                            setOffice()
                        }
                    })
            }

            commonRepo.getOffice("/api/auth/office/list/basic",
                object : OfficeDataValueListener {
                    override fun valueChange(list: List<Office>?) {
                        //   Log.e("gender", "gender message " + Gson().toJson(list))

                       if (mainOfficeList==null){
                           mainOfficeList = officeList
                           setOffice()
                       }
                    }
                })



            binding.llEmployee.setOnClickListener {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_messageFragment_to_searchEmployeeFragment)
            }



            binding.llEmployee.setOnClickListener {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_messageFragment_to_searchEmployeeFragment)
            }


            binding.send.setOnClickListener {
                uploadData()
            }
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData(
            "key",
            selectedDataList
        )?.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer { result ->

                result?.let {
                    if (it.size > 0) {
                        selectedDataList.addAll(it)
                        Log.e("emailfragment", "employee lsit : ${selectedDataList.size}")
                        binding.tvOffice.text = ""

                        selectedDataList?.forEach { element ->
                            binding.tvEmployee.append(
                                if (preparence.getLanguage().equals("en")) {
                                    "${element?.name},"
                                } else "${element?.name_bn},"
                            )
                        }


                        //selectedEmployeeList = it
                    }
                }
            })

        return binding.root
    }

    fun setOffice() {
        mainOfficeList?.let {
            SpinnerAdapter().setOfficeSpinner(
                binding?.spinner!!,
                context,
                it,
                0,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        office = any as Office
                        office?.name?.let {
                            officeList?.add(office!!)
                            binding.tvOffice.append(
                                if (preparence.getLanguage().equals("en")) {
                                    "${office?.name},"
                                } else "${office?.name_bn},"
                            )
                        }

                        Log.e("selected item", " item : " + office)
                    }

                }
            )
        }
    }

    fun getMapData(): HashMap<Any, Any?> {
        var localOfficeList = arrayListOf<Int>()
        var localEmployeeList = arrayListOf<Int>()
        officeList?.forEach { element ->
            element?.id?.let { localOfficeList.add(it) }
        }
        selectedDataList?.forEach { element ->
            element?.id?.let { localEmployeeList.add(it) }
        }


        var map = HashMap<Any, Any?>()
        map.put(
            "office_id",
            localOfficeList
        )
        map.put(
            "employee_id",
            localEmployeeList
        )
        map.put(
            "message_body",
            binding.etBody?.text?.trim().toString()
        )
        return map
    }


    private fun init() {
        messageViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(MessagingViewModel::class.java)
        utilViewmodel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(UtilViewModel::class.java)

    }


    fun uploadData() {
        var loadingDialog = CustomLoadingDialog().createLoadingDialog(activity)
        messageViewModel.sendSms(getMapData())
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                loadingDialog?.dismiss()
                showResponse(it)
            })
    }

    fun showResponse(any: Any?) {
        if (any is String) {
            toast(activity, any)

        } else if (any is ApiError) {
            try {
                if (any.getError().isEmpty()) {
                    toast(activity, any.getMessage())
                    Log.e("ok", "error")
                } else {
                    for (n in any.getError().indices) {
                        val error = any.getError()[n].getField()
                        val message = any.getError()[n].getMessage()
                        if (TextUtils.isEmpty(error)) {
                            message?.let {
                                binding?.tvBodyError?.visibility =
                                    View.VISIBLE
                                binding?.tvBodyError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                        }
                        Log.e("ok", "error ${message}")
                        when (error) {
                            "employee_id" -> {
                                binding?.tvEmployeeError?.visibility =
                                    View.VISIBLE
                                binding?.tvEmployeeError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "office_id" -> {
                                binding?.tvOfficeError?.visibility =
                                    View.VISIBLE
                                binding?.tvOfficeError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "message_body" -> {
                                binding?.tvBodyError?.visibility =
                                    View.VISIBLE
                                binding?.tvBodyError?.text =
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
            }
        }
    }

    fun invisiableAllError() {
        binding?.tvBodyError?.visibility =
            View.GONE
        binding?.tvOfficeError?.visibility =
            View.GONE
        binding?.tvEmployeeError?.visibility =
            View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }
}