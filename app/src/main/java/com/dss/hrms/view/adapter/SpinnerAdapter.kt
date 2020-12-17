package com.dss.hrms.view.adapter


import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.dss.hrms.R
import com.dss.hrms.model.Office
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.view.`interface`.CommonSpinnerSelectedItemListener
import com.namaztime.namaztime.database.MySharedPreparence


class SpinnerAdapter {


    fun setSpinner(
        spinner: Spinner,
        context: Context?,
        dataList: List<SpinnerDataModel>?,
        id: Int?,
        commonSpinnerSelectedItemListener: CommonSpinnerSelectedItemListener
    ) {
        var preparence: MySharedPreparence? = context?.let { MySharedPreparence(it) }
        var list = ArrayList<String?>()
        var selectedPosition = 0
        var i = 0
        dataList?.let {
            while (i < it.size) {
                if (preparence?.getLanguage().equals("en"))
                    list.add(dataList.get(i).name)
                else
                    list.add(dataList.get(i).name_bn)
                if (it.get(i).id == id) {
                    selectedPosition = i
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
        spinner.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                dataList?.let {
                    if (position < it.size)
                        commonSpinnerSelectedItemListener.selectedItem(dataList?.get(position))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

    }


    fun setOfficeSpinner(
        spinner: Spinner,
        context: Context?,
        dataList: List<Office>?,
        id: Int?,
        commonSpinnerSelectedItemListener: CommonSpinnerSelectedItemListener
    ) {
        var preparence: MySharedPreparence? = context?.let { MySharedPreparence(it) }
        var list = ArrayList<String?>()
        var selectedPosition = 0
        var i = 0
        dataList?.let {
            while (i < it.size) {
                if (preparence?.getLanguage().equals("en"))
                    list.add(dataList.get(i).name)
                else
                    list.add(dataList.get(i).name_bn)
                if (it.get(i).id == id) {
                    selectedPosition = i
                }
                i++
            }
        }
        if (list != null && list.size > 0 && context != null) {
            var adapter = ArrayAdapter(context!!, R.layout.spinner_layout, R.id.tvContent, list)
            adapter?.let { spinner.setAdapter(it) }
            spinner.setSelection(selectedPosition)
        }
        spinner.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                dataList?.let {
                    if (position < it.size)
                        commonSpinnerSelectedItemListener.selectedItem(dataList?.get(position))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

    }

    fun setSpinnerForStringMatch(
        spinner: Spinner,
        context: Context?,
        dataList: List<SpinnerDataModel>?,
        name: String?,
        commonSpinnerSelectedItemListener: CommonSpinnerSelectedItemListener
    ) {
        var preparence: MySharedPreparence? = context?.let { MySharedPreparence(it) }
        var list = ArrayList<String?>()
        var selectedPosition = 0
        var i = 0
        dataList?.let {
            while (i < it.size) {
                if (preparence?.getLanguage().equals("en"))
                    list.add(dataList.get(i).name)
                else
                    list.add(dataList.get(i).name_bn)
                if (it.get(i).name != null && it.get(i).name.equals("" + name)) {
                    selectedPosition = i
                }
                Log.e("datalist", "data : " + list.get(i))
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
        spinner.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                dataList?.let {
                    if (position < it.size)
                        commonSpinnerSelectedItemListener.selectedItem(dataList?.get(position))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

    }

}