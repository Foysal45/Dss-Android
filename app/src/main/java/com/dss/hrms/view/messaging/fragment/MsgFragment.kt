package com.dss.hrms.view.messaging.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dss.hrms.databinding.FragmentMsgBinding
import com.dss.hrms.view.messaging.model.MessageModel
import dagger.android.support.DaggerFragment

class MsgFragment : DaggerFragment() {
    lateinit var binding: FragmentMsgBinding
    var emp: String = ""
    var office: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMsgBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // recive the model sent from the prev page

        val msgModel: MessageModel? = arguments?.getSerializable("MSGMODEL") as MessageModel?

        binding.etBody.text = "${msgModel?.message_body}"

        // generate emp and offfice name
        for (e in msgModel?.employees!!) {
            emp = e?.name.toString() + " ,"
            office = e?.office?.office_name.toString() + " ,"
        }
        // remove the last "," from the string and set it to the text
        binding.officeName.text =  removeTrailingComma(office)
        binding.tvEmployee.text = removeTrailingComma(emp)


    }

    fun removeTrailingComma(s: String): String {
        val sb = StringBuilder(s)
        while (sb.isNotEmpty() && sb[sb.length - 1] == ',') {
            sb.setLength(sb.length - 1)
        }
        return sb.toString()
    }
}