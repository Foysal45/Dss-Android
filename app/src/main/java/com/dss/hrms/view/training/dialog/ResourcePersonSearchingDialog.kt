package com.dss.hrms.view.training.dialog

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogTrainingSearchBinding
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.view.allInterface.CommonDataValueListener
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.personalinfo.adapter.SpinnerAdapter
import com.dss.hrms.view.training.`interface`.OnResourcePersonValueListener
import com.dss.hrms.view.training.viewmodel.TrainingManagementViewModel
import java.util.HashMap
import javax.inject.Inject

class ResourcePersonSearchingDialog @Inject constructor() {

    @Inject
    lateinit var commonRepo: CommonRepo

    lateinit var trainingManagementViewModel: TrainingManagementViewModel

    lateinit var dialogCustome: Dialog
    lateinit var binding: DialogTrainingSearchBinding


    var context: Context? = null
    lateinit var onResourcePersonValueListener: OnResourcePersonValueListener
    var designation: SpinnerDataModel? = null

    fun showResourcePersonSearchDialog(
        context: Context?,
        //  onResourcePersonValueListener: OnResourcePersonValueListener,
        trainingManagementViewModel: TrainingManagementViewModel
    ) {
        dialogCustome = context?.let { Dialog(it) }!!
        dialogCustome?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.context = context
        //  this.onResourcePersonValueListener = onResourcePersonValueListener
        this.trainingManagementViewModel = trainingManagementViewModel
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_training_search,
            null,
            false
        )
        binding?.getRoot()?.let { dialogCustome?.setContentView(it) }

        var window: Window? = dialogCustome?.getWindow()
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        loadDesignationList()
        binding?.resourcePersonHeader.tvClose.setOnClickListener {
            dialogCustome.dismiss()
        }

        binding.llResourcePersonSearch.visibility = View.VISIBLE


        binding.search.setOnClickListener {
            searchOffice()
        }
        dialogCustome?.show()
    }

    fun searchOffice() {
        var dialog = CustomLoadingDialog().createLoadingDialog(context)
        trainingManagementViewModel.searchResourcePerson(
            getMapData(),
            object : OnResourcePersonValueListener {
                override fun onValueChange(list: List<TrainingResponse.ResourcePerson>?) {
                    Log.e("resourcePersonlist", " list : " + list?.size)
                    //  onResourcePersonValueListener.onValueChange(list)
                    dialog?.dismiss()
                    dialogCustome.dismiss()
                }
            })
    }

    fun getMapData(): HashMap<Any, Any?> {
        var map = HashMap<Any, Any?>()
        designation?.id?.let {
            map.put("person_name", it)
        }
        binding.resourcePersonShortName.etText.text.trim().toString()?.let {
            map.put("short_name", it)
        }
        binding.resourcePersonName.etText.text.trim().toString()?.let {
            map.put("person_name", it)
        }
        binding.resourcePersonFirstEmail.etText.text.trim().toString()?.let {
            map.put("first_email", it)
        }
        binding.resourcePersonFirstMobile.etText.text.trim().toString()?.let {
            map.put("first_mobile", it)
        }

        return map
    }

    fun loadDesignationList() {
        commonRepo.getCommonData("/api/auth/designation/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.resourcePersonDesignation.spinner,
                            context,
                            list,
                            0,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    designation = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })

    }
}