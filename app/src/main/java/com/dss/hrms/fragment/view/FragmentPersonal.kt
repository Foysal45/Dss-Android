package com.dss.hrms.fragment.view

import BaseModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaadride.shared_pref.SharedPref
import com.dss.hrms.R
import com.dss.hrms.fragment.adapter.RecyclerAdapter_box
import com.dss.hrms.fragment.model.Box
import com.dss.hrms.helper.LanguageChange
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_personal.*
import kotlinx.android.synthetic.main.fragment_personal.view.*

class FragmentPersonal : Fragment() {
    var boxList = mutableListOf<Box>()
    var recyclerAdapter_Box: RecyclerAdapter_box? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_personal, container, false)
        LanguageChange.languageSet(view.context)
        recyclerAdapter_Box = RecyclerAdapter_box(view.context, boxList)
        view.rec_red.layoutManager = LinearLayoutManager(view.context)
        view.rec_red.adapter = recyclerAdapter_Box

        var sharedPref = SharedPref(view.context)
        val text: String = sharedPref.json!!
        val baseModel = Gson().fromJson(text, BaseModel::class.java)

        boxList.clear()
        boxList.add(
            Box(
                getString(R.string.employee_id),
                getString(R.string.employee_id),
                "" + sharedPref.uEmployeeId
            )
        )
        boxList.add(
            Box(
                getString(R.string.name),
                getString(R.string.name),
                baseModel.data.g_name()
            )
        )
        boxList.add(
            Box(
                getString(R.string.name_b),
                getString(R.string.name_b),
                baseModel.data.g_name_bn()
            )
        )
        boxList.add(
            Box(
                getString(R.string.birth),
                getString(R.string.birth),
                baseModel.data.g_date_of_birth()
            )
        )
        boxList.add(
            Box(
                getString(R.string.f_name),
                getString(R.string.f_name),
                baseModel.data.g_fathers_name()
            )
        )
        boxList.add(
            Box(
                getString(R.string.f_name_b),
                getString(R.string.f_name_b),
                baseModel.data.g_fathers_name_bn()
            )
        )
        boxList.add(
            Box(
                getString(R.string.m_name),
                getString(R.string.m_name),
                baseModel.data.g_mothers_name()
            )
        )
        boxList.add(
            Box(
                getString(R.string.m_name_b),
                getString(R.string.m_name_b),
                baseModel.data.g_mothers_name_bn()
            )
        )
        if (baseModel.data.gender.g_name().equals("Male"))
            view.male.isChecked = true
        else
            view.female.isChecked = true

        recyclerAdapter_Box!!.notifyDataSetChanged()










        return view
    }


}