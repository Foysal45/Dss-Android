package com.dss.hrms.view.training.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dss.hrms.R
import com.dss.hrms.databinding.FragmentContentFaqBinding
import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.view.training.adaoter.FaqAdapter
import com.dss.hrms.view.training.viewmodel.ContentManagementViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class ContentFaqFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    lateinit var contentManagementViewModel: ContentManagementViewModel
    lateinit var binding: FragmentContentFaqBinding
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: FaqAdapter
    lateinit var dataList: List<TrainingResponse.Faq>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContentFaqBinding.inflate(inflater, container, false)

        init()

        contentManagementViewModel.apply {
            var dialog = CustomLoadingDialog().createLoadingDialog(activity)
            getFaq()
            faq.observe(viewLifecycleOwner, Observer {
                dialog?.dismiss()
                it?.let {
                    dataList = it
                    if (dataList != null)
                        prepareRecycleView()
                }
            })
        }
        return binding.root
    }

    private fun init() {
        contentManagementViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(ContentManagementViewModel::class.java)
    }

    private fun prepareRecycleView() {
        linearLayoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = linearLayoutManager
        adapter = FaqAdapter()
        activity?.let { adapter.setInitialData(it, dataList) }
        binding.recyclerView.adapter = adapter
    }

}