package com.dss.hrms.view.messaging.fragment

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogTrainingLoyeoutBinding
import com.dss.hrms.databinding.FragmentResourcePersonBinding
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.Operation
import com.dss.hrms.view.bottomsheet.SelectImageBottomSheet
import com.dss.hrms.view.messaging.adapter.MsgListAdapter
import com.dss.hrms.view.messaging.model.MessageModel
import com.dss.hrms.view.messaging.viewmodel.MessagingViewModel
import com.dss.hrms.view.training.dialog.ResourcePersonSearchingDialog
import com.dss.hrms.view.training.model.ExpertiseField
import com.dss.hrms.view.training.model.HonorariumHead
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import java.io.File
import javax.inject.Inject


class MsgListFragment : DaggerFragment() {
    // Storage Permissions
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val REQUEST_TAKE_PHOTO = 1
    private val REQUEST_SELECT_PHOTO = 2
    private var selectImageBottomSheet: SelectImageBottomSheet? = null
    private var imageFile: File? = null
    private var currentPhotoPath: String? = null

    @Inject
    lateinit var resourcePersonSearchingDialog: ResourcePersonSearchingDialog

    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var requestOption: RequestManager

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    lateinit var msgViewModel: MessagingViewModel

    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: MsgListAdapter
    lateinit var binding: FragmentResourcePersonBinding
    lateinit var dataLIst: List<MessageModel>
    lateinit var dialogTrainingLoyeoutBinding: DialogTrainingLoyeoutBinding


    var designation: SpinnerDataModel? = null
    var honorariumHead: HonorariumHead? = null
    var expertiseField: ExpertiseField? = null

    var imageUrl: String? = null
    var loadingDialog: Dialog? = null

    var dialogCustome: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResourcePersonBinding.inflate(inflater, container, false)

        init()
        val dialog = CustomLoadingDialog().createLoadingDialog(activity)

        msgViewModel.apply {
            getMessageList()
            msg.observe(viewLifecycleOwner, {
                dialog?.dismiss()
                it?.let {
                    dataLIst = it
                    Log.e("MEG", "hello ${dataLIst.size}")
                    prepareRecycleView()
                }

            })
        }

        binding.llSearch.visibility = View.GONE


        binding.fab.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_msgListFragment_to_messageFragment)
        }

        return binding.root
    }

    private fun init() {

        msgViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(MessagingViewModel::class.java)
        // val list = MySharedPreparence(binding.root.context).get("permissionList") as List<Any>?

    }

    fun prepareRecycleView() {
        linearLayoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = linearLayoutManager
        adapter = MsgListAdapter()
        activity?.let {
            adapter.setInitialData(dataLIst, it, object : MsgListAdapter.msgListClickListener {

                override fun onClick(model: MessageModel, position: Int, operation: Operation) {
                    when (operation) {
                        Operation.EDIT -> {

                            // change page and send the model with it
                            val bundle = bundleOf("MSGMODEL" to model)
                            findNavController().navigate(R.id.action_msgListFragment_to_msgFragment , bundle)
                        }

                    }
                }

            })
        }

        binding.recyclerView.adapter = adapter
    }


    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }

}