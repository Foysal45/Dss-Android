package com.dss.hrms.view.training.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
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
import com.dss.hrms.databinding.FragmentContentsContentBinding
import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.Operation
import com.dss.hrms.view.activity.WebViewActivity
import com.dss.hrms.view.training.`interface`.OnContentsContentClickListener
import com.dss.hrms.view.training.adaoter.ContentsContentAdapter
import com.dss.hrms.view.training.viewmodel.ContentManagementViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.personal_info_update_button.view.*
import javax.inject.Inject

class ContentsContentFragment : DaggerFragment() {


    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var preparence: MySharedPreparence

    lateinit var contentManageMentViewModel: ContentManagementViewModel
    lateinit var binding: FragmentContentsContentBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var dataList: List<TrainingResponse.Content>
    lateinit var adapter: ContentsContentAdapter

    var dialogCustome: Dialog? = null
    lateinit var dialogTrainingLoyeoutBinding: DialogTrainingLoyeoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    fun init() {
        contentManageMentViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(ContentManagementViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContentsContentBinding.inflate(inflater, container, false)
        init()


        binding.fab.setOnClickListener {
            Intent(activity,WebViewActivity::class.java).apply {
//                putExtra(
//                    "url",
//                    "http://dss.staging.simecsystem.com/app-ui/training-management/content/create?token=Bearer ${preparence.getToken()}&lang=${preparence.getLanguage()}"
//                )
                putExtra(
                    "url",
                    "http://dss.staging.simecsystem.com/auth/login"
                )

                putExtra(
                    "url",
                    "https://www.espncricinfo.com/series/bangladesh-in-nz-2020-21-1233967/new-zealand-vs-bangladesh-3rd-t20i-1233981/live-cricket-score"
                )

                startActivity(this)
            }

           //showEditCreateDialog(Operation.CREATE)
        }

        contentManageMentViewModel.apply {
            var dialog = CustomLoadingDialog().createLoadingDialog(activity)
            getContentList()
            content.observe(viewLifecycleOwner, Observer {
                dialog?.dismiss()
                it?.let {
                    dataList = it
                    prepareRecycleView()
                }

            })
        }


        return binding.root
    }

    private fun prepareRecycleView() {

        layoutManager = LinearLayoutManager(activity)
        adapter = ContentsContentAdapter()
        activity?.let {
            adapter.setInitialData(dataList, it, object : OnContentsContentClickListener {
                override fun onClick(
                    category: TrainingResponse.ContentsContent,
                    operation: Operation,
                    position: Int?
                ) {
                    Toast.makeText(activity, "" + operation, Toast.LENGTH_LONG).show()
                }

            })
        }
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

    }


    fun showEditCreateDialog(operation: Operation) {
        dialogCustome = activity?.let { Dialog(it) }
        dialogCustome?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogTrainingLoyeoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_training_loyeout,
            null,
            false
        )
        dialogTrainingLoyeoutBinding?.getRoot()?.let { dialogCustome?.setContentView(it) }
        var window: Window? = dialogCustome?.getWindow()
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialogTrainingLoyeoutBinding.llContentContent.visibility = View.VISIBLE


        //  dialogTrainingLoyeoutBinding.addContentWebView.getSettings().setJavaScriptEnabled(true);
        //dialogTrainingLoyeoutBinding.addContentWebView.loadUrl("http://www.google.com");
        dialogTrainingLoyeoutBinding.addContentWebView.getSettings()
            .setLoadsImagesAutomatically(true);
        dialogTrainingLoyeoutBinding.addContentWebView.getSettings().setJavaScriptEnabled(true);
        dialogTrainingLoyeoutBinding.addContentWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        dialogTrainingLoyeoutBinding.addContentWebView.loadUrl("http://www.google.com");

        dialogCustome?.show()
    }


}