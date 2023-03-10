package com.dss.hrms.view.training.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogTrainingLoyeoutBinding
import com.dss.hrms.databinding.FragmentContentCategoryBinding
import com.dss.hrms.model.JsonKeyReader
import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.model.error.ApiError
import com.dss.hrms.model.error.ErrorUtils2
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.Operation
import com.dss.hrms.util.Operation.*
import com.dss.hrms.view.personalinfo.EmployeeInfoActivity
import com.dss.hrms.view.training.`interface`.OnContentCategoryClickListener
import com.dss.hrms.view.training.adaoter.ContentCategoryAdapter
import com.dss.hrms.view.training.viewmodel.ContentManagementViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.personal_info_update_button.view.*
import javax.inject.Inject


class ContentCategoryFragment : DaggerFragment() {


    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var preparence: MySharedPreparence
    lateinit var binding: FragmentContentCategoryBinding;

    lateinit var contentManagementViewModel: ContentManagementViewModel
    var dialogCustome: Dialog? = null
    lateinit var linearLayoutrManager: LinearLayoutManager
    lateinit var contentCategoryAdapter: ContentCategoryAdapter
    lateinit var list: List<TrainingResponse.Category>
    lateinit var dialogTrainingLoyeoutBinding: DialogTrainingLoyeoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContentCategoryBinding.inflate(inflater, container, false)
        init()


        contentManagementViewModel.apply {
            getCategory()
            var dialog = CustomLoadingDialog().createLoadingDialog(activity)

            category.observe(viewLifecycleOwner, Observer {
                dialog?.dismiss()
                it?.let {
                    Log.e("category", "category ${(it).get(0).category_name}")
                    list = it
                    prepareRecycleView()
                }
            })
        }

        binding.fab.setOnClickListener {
            showEditCreateDialog(CREATE, null)
        }
        return binding.root
    }


    fun prepareRecycleView() {
        linearLayoutrManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = linearLayoutrManager
        contentCategoryAdapter = ContentCategoryAdapter()
        activity?.let {
            contentCategoryAdapter.setRequiredData(
                list,
                it,
                object : OnContentCategoryClickListener {
                    override fun onClick(
                        category: TrainingResponse.Category,
                        operation: Operation,
                        position: Int?
                    ) {
                        when (operation) {
                            EDIT -> {
                                val list = preparence.get("permissionList") as List<Any>?

                                if (JsonKeyReader.hasPermission(
                                        "contentmanagementcategory-edit",
                                        list
                                    )
                                ) {
                                    showEditCreateDialog(operation, category)
                                }

                            }//

                            DELETE -> {
                            }
                            VIEW -> TODO()
                            CREATE -> TODO()
                        }
                        //   Toast.makeText(activity, "" + operation, Toast.LENGTH_LONG).show()
                    }

                })
        }
        binding.recyclerView.adapter = contentCategoryAdapter

    }

    fun init() {
        contentManagementViewModel =
            ViewModelProvider(
                this,
                viewModelProviderFactory
            ).get(ContentManagementViewModel::class.java)

        val list = preparence.get("permissionList") as List<Any>?

        if (!JsonKeyReader.hasPermission(
                "contentmanagementcategory-add",
                list
            )
        ) {
            binding.fab.visibility = View.GONE
        }
    }


    fun showEditCreateDialog(operation: Operation, category: TrainingResponse.Category?) {
        dialogCustome = activity?.let { Dialog(it) }
        dialogCustome?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogTrainingLoyeoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_training_loyeout,
            null,
            false
        )
        dialogTrainingLoyeoutBinding.root.let { dialogCustome?.setContentView(it) }
        var window: Window? = dialogCustome?.window
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialogTrainingLoyeoutBinding.llAddCategory.visibility = View.VISIBLE
        dialogTrainingLoyeoutBinding.categoryNameEn.etText.setText(category?.category_name)
        dialogTrainingLoyeoutBinding.categoryNameBn.etText.setText(category?.category_name_bn)
        dialogTrainingLoyeoutBinding.categoryHeader.tvClose.setOnClickListener { dialogCustome?.dismiss() }
        dialogTrainingLoyeoutBinding.categoryHeader.tvTitle.setText(getString(R.string.update_category))

        if (operation == EDIT) dialogTrainingLoyeoutBinding.categoryUpdateButton.btnUpdate.setText(
            getString(R.string.update)
        ) else dialogTrainingLoyeoutBinding.categoryUpdateButton.btnUpdate.setText(getString(R.string.create))
        dialogTrainingLoyeoutBinding.categoryUpdateButton.btnUpdate.setOnClickListener {
            invisiableAllError()
            var dialog = CustomLoadingDialog().createLoadingDialog(activity)
            if (operation == CREATE) {
                contentManagementViewModel.addCategory(getMapData(category))
                    .observe(viewLifecycleOwner,
                        Observer {
                            dialog?.dismiss()
                            showResponse(it)
                        })
            } else if (operation == EDIT) {
                contentManagementViewModel.updateCategory(getMapData(category), category?.id!!)
                    .observe(viewLifecycleOwner,
                        Observer {
                            dialog?.dismiss()
                            showResponse(it)
                        })
            }
        }

        dialogCustome?.show()
    }


    fun getMapData(category: TrainingResponse.Category?): HashMap<Any, Any> {
        var map = HashMap<Any, Any>()
        map.put(
            "category_name",
            dialogTrainingLoyeoutBinding.categoryNameEn.etText.text.trim().toString()
        )
        map.put(
            "category_name_bn",
            dialogTrainingLoyeoutBinding.categoryNameBn.etText.text.trim().toString()
        )
        map.put("status", 1)

        return map
    }


    fun showResponse(any: Any) {
        if (any is String) {
            toast(activity, any)
            contentManagementViewModel.getCategory()
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
                                dialogTrainingLoyeoutBinding?.categoryNameBn?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.categoryNameBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                        }
                        when (error) {
                            "category_name" -> {
                                dialogTrainingLoyeoutBinding?.categoryNameEn?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.categoryNameEn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "category_name_bn" -> {
                                dialogTrainingLoyeoutBinding?.categoryNameBn?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.categoryNameBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                toast(EmployeeInfoActivity.context, e.toString())
            }

        } else if (any is Throwable) {
            toast(EmployeeInfoActivity.context, any.toString())
        } else {
            EmployeeInfoActivity?.context?.getString(R.string.failed)?.let {
                toast(
                    EmployeeInfoActivity.context,
                    it
                )
            }
        }
    }

    fun invisiableAllError() {
        dialogTrainingLoyeoutBinding.categoryNameEn.tvError.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.categoryNameBn.tvError.visibility =
            View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }


}