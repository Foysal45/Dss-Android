package com.dss.hrms.view.personalinfo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.R
import com.dss.hrms.databinding.ModelEmployeeInfoBinding
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.util.StaticKey
import com.dss.hrms.view.allInterface.OnEmployeeInfoClickListener
import com.dss.hrms.view.personalinfo.adapter.employeeInfo.EmployeeInfoDataBinding
import com.namaztime.namaztime.database.MySharedPreparence
import javax.inject.Inject

class EmployeeInfoAdapter @Inject constructor() :
    RecyclerView.Adapter<EmployeeInfoAdapter.ViewHolder>() {

    @Inject
    lateinit var employeeInfoDataBinding: EmployeeInfoDataBinding

    @Inject
    lateinit var preparence: MySharedPreparence

    lateinit var context: Context

    private lateinit var dataList: List<Any>

    lateinit var key: String
    lateinit var onEmployeeInfoClickListener: OnEmployeeInfoClickListener

    fun setRequiredData(
        dataList: List<Any>,
        key: String,
        context: Context,
        onEmployeeInfoClickListener: OnEmployeeInfoClickListener
    ) {
        this.dataList = dataList
        this.key = key
        this.context = context
        this.onEmployeeInfoClickListener = onEmployeeInfoClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ModelEmployeeInfoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.model_employee_info,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //   Log.e("adapter","adapter data  ; "+dataList.size)

        when (key) {
            StaticKey.PERMANENT_ADDRESS -> {
                holder.binding?.hAddress?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key, position)
                })
                holder.binding?.llAddress?.visibility = View.VISIBLE
                employeeInfoDataBinding.bindPermanentAddressData(
                    holder.binding!!,
                    dataList.get(position) as Employee.PermanentAddresses,
                    context,
                    context.getString(R.string.permanent_address)
                )

            }
            StaticKey.PRESENT_ADDRESS -> {
                holder.binding?.hAddress?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key, position)
                })
                holder.binding?.llAddress?.visibility = View.VISIBLE
                employeeInfoDataBinding.bindPrensentAddressData(
                    holder.binding!!,
                    dataList.get(position) as Employee.PresentAddresses,
                    context,
                    context.getString(R.string.present_address)
                )
            }
            StaticKey.EducationalQualifications -> {
                holder.binding?.hEducationQualification?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key, position)
                })
                holder.binding?.llEducationQualificaion?.visibility = View.VISIBLE
                employeeInfoDataBinding.bindEducationalQualificationData(
                    holder.binding!!,
                    dataList.get(position) as Employee.EducationalQualifications,
                    context,
                    context.getString(R.string.educational_qualification)
                )
            }
            StaticKey.Jobjoining -> {
                holder.binding?.hJobJoiningInformation?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key, position)
                })
                holder.binding?.llJobjoningInfo?.visibility = View.VISIBLE
                employeeInfoDataBinding.bindJobjoningInfoData(
                    holder.binding!!,

                    dataList.get(position) as Employee.Jobjoinings,
                    context,
                    context.getString(R.string.job_joining_information)
                )
            }
            StaticKey.Quota -> {
                holder.binding?.hQuota?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key, position)
                })
                holder.binding?.llQuotaInfo?.visibility = View.VISIBLE
                employeeInfoDataBinding.bindQuotaData(
                    holder.binding!!,
                    dataList.get(position) as Employee.EmployeeQuotas,
                    context,
                    context.getString(R.string.quota_information)
                )
            }
            StaticKey.Spouse -> {
                holder.binding?.hSpouse?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key, position)
                })
                holder.binding?.llSpouse?.visibility = View.VISIBLE
                employeeInfoDataBinding.bindSpouseData(
                    holder.binding!!,
                    dataList.get(position) as Employee.Spouses,
                    context,
                    context.getString(R.string.spouse)
                )
            }
            StaticKey.Children -> {
                holder.binding?.hChildren?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key, position)
                })
                holder.binding?.llChildrenInfo?.visibility = View.VISIBLE
                employeeInfoDataBinding.bindChildrenData(
                    holder.binding!!,
                    dataList.get(position) as Employee.Childs,
                    context,
                    context.getString(R.string.child_information)
                )
            }
            StaticKey.Language -> {
                holder.binding?.hLanguage?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key, position)
                })
                holder.binding?.llLanguageInfo?.visibility = View.VISIBLE
                employeeInfoDataBinding.bindLanguageData(
                    holder.binding!!,
                    dataList.get(position) as Employee.Languages,
                    context,
                    context.getString(R.string.language_information)
                )
            }

            StaticKey.LocalTraining -> {
                holder.binding?.hLocaltraining?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key, position)
                })
                holder.binding?.llLocalTrainingInfo?.visibility = View.VISIBLE
                employeeInfoDataBinding.bindLocaltrainingData(
                    holder.binding!!,
                    dataList.get(position) as Employee.LocalTrainings,
                    context,
                    context.getString(R.string.local_training)
                )
            }

            StaticKey.ForeingTraining -> {
                holder.binding?.hLocaltraining?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key, position)
                })
                holder.binding?.llLocalTrainingInfo?.visibility = View.VISIBLE
                employeeInfoDataBinding.bindForeignTrainingData(
                    holder.binding!!,
                    dataList.get(position) as Employee.Foreigntrainings,
                    context,
                    context.getString(R.string.foreign_training)
                )
            }

            StaticKey.OfficialResidentials -> {
                holder.binding?.hOfficialResidentialInfo?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key, position)
                })
                holder.binding?.llOfficialResidentialInfo?.visibility = View.VISIBLE
                employeeInfoDataBinding.bindOfficialResidentialInfoData(
                    holder.binding!!,
                    dataList.get(position) as Employee.OfficialResidentials,
                    context,
                    context.getString(R.string.official_residential_information)
                )
            }

            StaticKey.ForeignTravel -> {
                holder.binding?.hForeignTravelInfo?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key, position)
                })
                holder.binding?.llForeingTravelInfo?.visibility = View.VISIBLE
                employeeInfoDataBinding.bindForeignTravelInfoData(
                    holder.binding!!,
                    dataList.get(position) as Employee.ForeignTravels,
                    context,
                    context.getString(R.string.foreign_travel)
                )
            }

            StaticKey.AdditionalQualifications -> {
                holder.binding?.hAdditionalQualification?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key, position)
                })
                holder.binding?.llAdditionalQualification?.visibility = View.VISIBLE
                employeeInfoDataBinding.bindAdditionalQualificationData(
                    holder.binding!!,
                    dataList.get(position) as Employee.AdditionalQualifications,
                    context,
                    context.getString(R.string.additional_professional_qualification)
                )
            }

            StaticKey.Publication -> {
                holder.binding?.hPublication?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key, position)
                })
                holder.binding?.llPublication?.visibility = View.VISIBLE
                employeeInfoDataBinding.bindPublicationsData(
                    holder.binding!!,
                    dataList.get(position) as Employee.Publications,
                    context,
                    context.getString(R.string.publication)
                )
            }

            StaticKey.HonoursAwards -> {
                holder.binding?.hHonoursAndAward?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key, position)
                })
                holder.binding?.llHonoursAndAward?.visibility = View.VISIBLE
                employeeInfoDataBinding.bindHonoursAndAwardData(
                    holder.binding!!,
                    dataList.get(position) as Employee.HonoursAwards,
                    context,
                    context.getString(R.string.honours_and_award)
                )
            }

            StaticKey.PostingRecord -> {
                holder.binding?.hPostingRecord?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key, position)
                })
                holder.binding?.llPostingRecord?.visibility = View.VISIBLE
                employeeInfoDataBinding.bindPostingRecordData(
                    holder.binding!!,
                    dataList.get(position) as Employee.PostingRecords,
                    context,
                    context.getString(R.string.posting_record)
                )
            }

            StaticKey.DisciplinaryAction -> {
                holder.binding?.hDiscipilinaryAction?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key, position)
                })
                holder.binding?.llDisciplinaryAction?.visibility = View.VISIBLE
                employeeInfoDataBinding.bindDisciplinaryActionsData(
                    holder.binding!!,
                    dataList.get(position) as Employee.DisciplinaryAction,
                    context,
                    context.getString(R.string.disciplinary_action)
                )
            }

            StaticKey.Promotion -> {
                holder.binding?.hPromotion?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key, position)
                })
                holder.binding?.llPromotion?.visibility = View.VISIBLE
                employeeInfoDataBinding.bindPromotionData(
                    holder.binding!!,
                    dataList.get(position) as Employee.Promotions,
                    context,
                    context.getString(R.string.promotion)
                )
            }

            StaticKey.References -> {
                holder.binding?.llReference?.visibility = View.VISIBLE
                holder.binding?.hReference?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key, position)
                })
                employeeInfoDataBinding.bindReferencesData(
                    holder.binding!!,
                    dataList.get(position) as Employee.References,
                    context,
                    context.getString(R.string.reference)
                )
            }


        }


    }

    //the class is hodling the list view
    class ViewHolder(binding: ModelEmployeeInfoBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ModelEmployeeInfoBinding? = null

        init {
            this.binding = binding
        }
    }


    override fun getItemCount(): Int {
        return dataList?.size!!
    }
}
