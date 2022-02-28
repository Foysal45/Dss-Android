package com.dss.hrms.view.messaging.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dss.hrms.R
import com.dss.hrms.databinding.FragmentTopMessagingBinding
import dagger.android.support.DaggerFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class TopMessagingFragment : DaggerFragment() {


    lateinit var binding: FragmentTopMessagingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTopMessagingBinding.inflate(inflater, container, false)


        binding.cardMessage.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_topMessagingFragment_to_msgListFragment)
        }

        binding.cardEmail.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_topMessagingFragment_to_emailFragment)
        }
        return binding.root
    }
}