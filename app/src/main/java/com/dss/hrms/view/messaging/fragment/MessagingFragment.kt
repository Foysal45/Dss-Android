package com.dss.hrms.view.messaging.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.dss.hrms.R
import com.dss.hrms.databinding.FragmentMessageBinding
import com.dss.hrms.model.Office
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.view.adapter.SpinnerAdapter
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.allInterface.OfficeDataValueListener
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class MessagingFragment : DaggerFragment() {
    @Inject
    lateinit var commonRepo: CommonRepo
    var office: Office? = null

    lateinit var binding: FragmentMessageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageBinding.inflate(inflater, container, false)



        commonRepo.getOffice("/api/auth/office/list/basic",
            object : OfficeDataValueListener {
                override fun valueChange(list: List<Office>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setOfficeSpinner(
                            binding?.spinner!!,
                            context,
                            list,
                            0,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    office = any as Office
                                    Log.e("selected item", " item : " + office?.name)
                                }

                            }
                        )
                    }
                }
            })


        binding.llEmployee.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_messageFragment_to_searchEmployeeFragment)
        }

        return binding.root
    }

}