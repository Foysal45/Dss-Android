package com.dss.hrms.view.training.fragment

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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

            val url = "http://dss.dev.simecsystem.com:10015/app-ui/training-management/content/create?token=${preparence.getToken()}&lang=${preparence.getLanguage()}"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)

//            Intent(activity,WebViewActivity::class.java).apply {
//                putExtra(
//                    "url",
//                    "http://dss.dev.simecsystem.com/payroll-management/employee-salary-process"
//                    //"http://dss.dev.simecsystem.com:10015/app-ui/training-management/content/create?token=${preparence.getToken()}&lang=${preparence.getLanguage()}",
//                       //   "http://dss.dev.simecsystem.com:10015/app-ui/training-management/content/create?lang=en&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI4MSIsImp0aSI6ImI5ZjAwYzBjYTgxOWQwYjE5NmU5ZGVhZWMzOGEzZTBiNDkyZDg1MDc3M2JmZmQwODA2ZjFiNjI5YmQxNTIwY2RjZWQ1MTQzZjRlYTY5NmZlIiwiaWF0IjoxNjIwNTM5NjE5LCJuYmYiOjE2MjA1Mzk2MTksImV4cCI6MTY1MjA3NTYxOSwic3ViIjoiMzMxIiwic2NvcGVzIjpbXX0.dGa3M341OsMABi3WuSH9WzqXlcr2ix629PkrT6G3XitYvGOAHgK_u4v9cBHqT5J4cMt46oy7w9uAuuF3hHFwKsr5AXJDlSKSqi17umLhNy99OvCRrIRbrgQ3SHNU46CW8LFAypEGDIWRtLjiluq_IAWrOC-9rMMVlFW6k9DHI0zHSx_Uq66A2uKzT3vHDE22BnYDBzVS2CLx9sz89bD4oapEbA3OxhrHurbxcwzGl6eEEPXp7iYZD2XeXiSW3M3ZH_BQJtfWFGxw7jvw61wzILcQV3Kdi4bwVUwkxp81EM6m3iQQ-Ju5vKl5PYPSjQknW49hg3kqy3Jlid8p1o2ZU4XI0Fa2yVClQXRA3o6TIO8MNK-BRc5qDvBgAwyCLM5x-FFwKfbMUSeHH_5mQQ_YJsSTFloULeBCXkiulutDuTl1YwCtchLSyHYup3pVaZb635Xg4_OZANn8jCuq5f6m7I2IahgnshO2anGogb7Xg7VOQcHM7oVS3QPmxqn7sBFd4I8yL5VfCADTl6Y0kcs7WTB9LNF_MJtHtWlRk0mFBF9eO9za8YLSyCWFuKNqVI804gahADbHp5j7K1TeXlE7fN64r9GPDngbfuW0YOVBZ22DgfFIJxQjjFPqJCdUSlDt7V6aXeSmkvSZOEriG3QgDyEVUhenk_J_pBXQQj1qhd8"
//                )
//                putExtra(
//                    "url",
//                    "http://dss.dev.simecsystem.com/auth/login"
//               )
////
////                putExtra(
////                    "url",
////                    "https://www.espncricinfo.com/series/bangladesh-in-nz-2020-21-1233967/new-zealand-vs-bangladesh-3rd-t20i-1233981/live-cricket-score"
////                )
////
//               startActivity(this)
//            }
//
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