package com.dss.hrms.view.training.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dss.hrms.R
import com.dss.hrms.databinding.FragmentContentCategoryBinding
import com.dss.hrms.databinding.FragmentContentsContentBinding
import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.Operation
import com.dss.hrms.view.training.`interface`.OnContentsContentClickListener
import com.dss.hrms.view.training.adaoter.ContentsContentAdapter
import com.dss.hrms.view.training.viewmodel.ContentManagementViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ContentsContentFragment : DaggerFragment() {


    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    lateinit var contentManageMentViewModel: ContentManagementViewModel
    lateinit var binding: FragmentContentsContentBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var dataList: List<TrainingResponse.Content>
    lateinit var adapter: ContentsContentAdapter


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


        contentManageMentViewModel.apply {
            var dialog = CustomLoadingDialog().createLoadingDialog(activity)
            getContentList()
            content.observe(viewLifecycleOwner, Observer {
                dialog?.dismiss()
                it?.let {
                    dataList = it
                    dataList += it
                    dataList += it
                    dataList += it
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
}