package com.dss.hrms.view.personalinfo.adapter

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.dss.hrms.R
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.model.Union
import com.dss.hrms.model.Upazilas
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.namaztime.namaztime.database.MySharedPreparence

class UnionAdapter {

    fun setUnionSpinner(
        spinner: Spinner,
        context: Context?,
        dataList: List<Union>?,
        id: Int?,
        commonSpinnerSelectedItemListener: CommonSpinnerSelectedItemListener
    ) {
        var preparence: MySharedPreparence? = context?.let { MySharedPreparence(it) }
        var list = ArrayList<String?>()
        var selectedPosition = 0
        var i = 0
        list.add(context?.getString(R.string.select_option))
        dataList?.let {
            while (i < it.size) {
                if (preparence?.getLanguage().equals("en"))
                    list.add(dataList.get(i).name)
                else
                    list.add(dataList.get(i).name_bn)
                if (it.get(i).id == id) {
                    selectedPosition = i + 1
                }
                i++
            }
        }
        if (list != null && list.size > 0 && context != null) {
            var adapter = ArrayAdapter(context!!, R.layout.spinner_layout, R.id.tvContent, list)
            adapter?.let { spinner.setAdapter(it) }
            spinner.setSelection(selectedPosition)
        }

//        if (districtListStr.size() > 1 && selectedPosition >= 0) binding.spDistrict.setSelection(
//            selectedPosition + 1
//        )
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                dataList?.let {
                    if (position <= it.size && position > 0)
                        commonSpinnerSelectedItemListener.selectedItem(dataList?.get(position - 1))
                    else {
                        if (position == 0)
                            commonSpinnerSelectedItemListener.selectedItem(null)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

    }



}