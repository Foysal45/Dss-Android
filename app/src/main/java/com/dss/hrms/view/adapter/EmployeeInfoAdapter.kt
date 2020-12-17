package com.dss.hrms.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.R
import com.dss.hrms.databinding.ModelEmployeeInfoBinding
import com.dss.hrms.model.Employee
import com.dss.hrms.util.StaticKey
import com.dss.hrms.view.`interface`.OnEmployeeInfoClickListener
import com.dss.hrms.view.adapter.employeeInfo.EmployeeInfoDataBinding
import com.dss.hrms.view.adapter.employeeInfo.EmployeeInfoDataBindingAddress
import com.namaztime.namaztime.database.MySharedPreparence

class EmployeeInfoAdapter(
    val context: Context,
    dataList: List<Any>,
    key: String,
    onEmployeeInfoClickListener: OnEmployeeInfoClickListener
) :
    RecyclerView.Adapter<EmployeeInfoAdapter.ViewHolder>() {

    private var dataList: List<Any>
    lateinit private var ctx: Context
    lateinit var preparence: MySharedPreparence
    lateinit var key: String
    lateinit var onEmployeeInfoClickListener: OnEmployeeInfoClickListener

    init {
        this.dataList = dataList
        this.ctx = context
        preparence = MySharedPreparence(ctx)
        this.key = key
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
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key)
                })
                holder.binding?.llAddress?.visibility = View.VISIBLE
                EmployeeInfoDataBindingAddress().bindPermanentAddressData(
                    holder.binding!!,
                    dataList.get(position) as Employee.PermanentAddresses,
                    context,
                    preparence,
                    context.getString(R.string.permanent_address)
                )

            }
            StaticKey.PRESENT_ADDRESS -> {
                holder.binding?.hAddress?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key)
                })
                holder.binding?.llAddress?.visibility = View.VISIBLE
                EmployeeInfoDataBindingAddress().bindPrensentAddressData(
                    holder.binding!!,
                    dataList.get(position) as Employee.PresentAddresses,
                    context,
                    preparence,
                    context.getString(R.string.present_address)
                )
            }
            StaticKey.EducationalQualifications -> {
                holder.binding?.hEducationQualification?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key)
                })
                holder.binding?.llEducationQualificaion?.visibility = View.VISIBLE
                EmployeeInfoDataBinding().bindEducationalQualificationData(
                    holder.binding!!,
                    dataList.get(position) as Employee.EducationalQualifications,
                    context,
                    preparence,
                    context.getString(R.string.educational_qualification)
                )
            }
            StaticKey.Jobjoining -> {
                holder.binding?.hJobJoiningInformation?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key)
                })
                holder.binding?.llJobjoningInfo?.visibility = View.VISIBLE
                EmployeeInfoDataBinding().bindJobjoningInfoData(
                    holder.binding!!,
                    dataList.get(position) as Employee.Jobjoinings,
                    context,
                    preparence,
                    context.getString(R.string.job_joining_information)
                )
            }
            StaticKey.Quota -> {
                holder.binding?.hQuota?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key)
                })
                holder.binding?.llQuotaInfo?.visibility = View.VISIBLE
                EmployeeInfoDataBinding().bindQuotaData(
                    holder.binding!!,
                    dataList.get(position) as Employee.EmployeeQuotas,
                    context,
                    preparence,
                    context.getString(R.string.quota_information)
                )
            }
            StaticKey.Spouse -> {
                holder.binding?.hSpouse?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key)
                })
                holder.binding?.llSpouse?.visibility = View.VISIBLE
                EmployeeInfoDataBinding().bindSpouseData(
                    holder.binding!!,
                    dataList.get(position) as Employee.Spouses,
                    context,
                    preparence,
                    context.getString(R.string.spouse)
                )
            }
            StaticKey.Children -> {
                holder.binding?.hChildren?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key)
                })
                holder.binding?.llChildrenInfo?.visibility = View.VISIBLE
                EmployeeInfoDataBinding().bindChildrenData(
                    holder.binding!!,
                    dataList.get(position) as Employee.Childs,
                    context,
                    preparence,
                    context.getString(R.string.child_information)
                )
            }
            StaticKey.Language -> {
                holder.binding?.hLanguage?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key)
                })
                holder.binding?.llLanguageInfo?.visibility = View.VISIBLE
                EmployeeInfoDataBinding().bindLanguageData(
                    holder.binding!!,
                    dataList.get(position) as Employee.Languages,
                    context,
                    preparence,
                    context.getString(R.string.language_information)
                )
            }

            StaticKey.LocalTraining -> {
                holder.binding?.hLocaltraining?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key)
                })
                holder.binding?.llLocalTrainingInfo?.visibility = View.VISIBLE
                EmployeeInfoDataBinding().bindLocaltrainingData(
                    holder.binding!!,
                    dataList.get(position) as Employee.LocalTrainings,
                    context,
                    preparence,
                    context.getString(R.string.local_training)
                )
            }

            StaticKey.ForeingTraining -> {
                holder.binding?.hLocaltraining?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key)
                })
                holder.binding?.llLocalTrainingInfo?.visibility = View.VISIBLE
                EmployeeInfoDataBinding().bindForeignTrainingData(
                    holder.binding!!,
                    dataList.get(position) as Employee.Foreigntrainings,
                    context,
                    preparence,
                    context.getString(R.string.foreign_training)
                )
            }

            StaticKey.OfficialResidentials -> {
                holder.binding?.hOfficialResidentialInfo?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key)
                })
                holder.binding?.llOfficialResidentialInfo?.visibility = View.VISIBLE
                EmployeeInfoDataBinding().bindOfficialResidentialInfoData(
                    holder.binding!!,
                    dataList.get(position) as Employee.OfficialResidentials,
                    context,
                    preparence,
                    context.getString(R.string.official_residential_information)
                )
            }

            StaticKey.ForeignTravel -> {
                holder.binding?.hForeignTravelInfo?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key)
                })
                holder.binding?.llForeingTravelInfo?.visibility = View.VISIBLE
                EmployeeInfoDataBinding().bindForeignTravelInfoData(
                    holder.binding!!,
                    dataList.get(position) as Employee.ForeignTravels,
                    context,
                    preparence,
                    context.getString(R.string.foreign_travel)
                )
            }

            StaticKey.AdditionalQualifications -> {
                holder.binding?.hAdditionalQualification?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key)
                })
                holder.binding?.llAdditionalQualification?.visibility = View.VISIBLE
                EmployeeInfoDataBinding().bindAdditionalQualificationData(
                    holder.binding!!,
                    dataList.get(position) as Employee.AdditionalQualifications,
                    context,
                    preparence,
                    context.getString(R.string.additional_professional_qualification)
                )
            }

            StaticKey.Publication -> {
                holder.binding?.hPublication?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key)
                })
                holder.binding?.llPublication?.visibility = View.VISIBLE
                EmployeeInfoDataBinding().bindPublicationsData(
                    holder.binding!!,
                    dataList.get(position) as Employee.Publications,
                    context,
                    preparence,
                    context.getString(R.string.publication)
                )
            }

            StaticKey.HonoursAwards -> {
                holder.binding?.hHonoursAndAward?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key)
                })
                holder.binding?.llHonoursAndAward?.visibility = View.VISIBLE
                EmployeeInfoDataBinding().bindHonoursAndAwardData(
                    holder.binding!!,
                    dataList.get(position) as Employee.HonoursAwards,
                    context,
                    preparence,
                    context.getString(R.string.honours_and_award)
                )
            }

            StaticKey.PostingRecord -> {
                holder.binding?.hPostingRecord?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key)
                })
                holder.binding?.llPostingRecord?.visibility = View.VISIBLE
                EmployeeInfoDataBinding().bindPostingRecordData(
                    holder.binding!!,
                    dataList.get(position) as Employee.PostingRecords,
                    context,
                    preparence,
                    context.getString(R.string.posting_record)
                )
            }

            StaticKey.DisciplinaryAction -> {
                holder.binding?.hDiscipilinaryAction?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key)
                })
                holder.binding?.llDisciplinaryAction?.visibility = View.VISIBLE
                EmployeeInfoDataBinding().bindDisciplinaryActionsData(
                    holder.binding!!,
                    dataList.get(position) as Employee.DisciplinaryAction,
                    context,
                    preparence,
                    context.getString(R.string.disciplinary_action)
                )
            }

            StaticKey.Promotion -> {
                holder.binding?.hPromotion?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key)
                })
                holder.binding?.llPromotion?.visibility = View.VISIBLE
                EmployeeInfoDataBinding().bindPromotionData(
                    holder.binding!!,
                    dataList.get(position) as Employee.Promotions,
                    context,
                    preparence,
                    context.getString(R.string.promotion)
                )
            }

            StaticKey.References -> {
                holder.binding?.llReference?.visibility = View.VISIBLE
                holder.binding?.hReference?.tvEdit?.setOnClickListener({
                    onEmployeeInfoClickListener.onClick(dataList.get(position), key)
                })
                EmployeeInfoDataBinding().bindReferencesData(
                    holder.binding!!,
                    dataList.get(position) as Employee.References,
                    context,
                    preparence,
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
