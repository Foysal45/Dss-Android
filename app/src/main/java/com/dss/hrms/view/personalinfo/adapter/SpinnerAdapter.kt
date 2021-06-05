package com.dss.hrms.view.personalinfo.adapter


import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.dss.hrms.R
import com.dss.hrms.model.Office
import com.dss.hrms.model.Paysacle
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
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
        spinner.setOnItemSelectedListener(object : OnItemSelectedListener {
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
                            commonSpinnerSelectedItemListener.selectedItem(SpinnerDataModel())
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

    }

  fun setEmployeeTypeSpinner(
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
        list.add(context?.getString(R.string.select_option))
        dataList?.let {
            while (i < it.size) {
                if (preparence?.getLanguage().equals("en"))
                    list.add(dataList.get(i).employee_type)
                else
                    list.add(dataList.get(i).employee_type_bn)
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
        spinner.setOnItemSelectedListener(object : OnItemSelectedListener {
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
                            commonSpinnerSelectedItemListener.selectedItem(SpinnerDataModel())
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

    }


    fun setDisabilityDegreeSpinner(
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
        list.add(context?.getString(R.string.select_option))
        dataList?.let {
            while (i < it.size) {
                if (preparence?.getLanguage().equals("en"))
                    list.add(dataList.get(i).disability_degree)
                else
                    list.add(dataList.get(i).disability_degree_bn)
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
        spinner.setOnItemSelectedListener(object : OnItemSelectedListener {
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
                            commonSpinnerSelectedItemListener.selectedItem(SpinnerDataModel())
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

    }

    fun setDisabilityTypeSpinner(
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
        list.add(context?.getString(R.string.select_option))
        dataList?.let {
            while (i < it.size) {
                if (preparence?.getLanguage().equals("en"))
                    list.add(dataList.get(i).disability_type)
                else
                    list.add(dataList.get(i).disability_type_bn)
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
        spinner.setOnItemSelectedListener(object : OnItemSelectedListener {
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
                            commonSpinnerSelectedItemListener.selectedItem(SpinnerDataModel())
                    }
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
        list.add(context?.getString(R.string.select_option))
        var selectedPosition = 0
        var i = 0
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
        spinner.setOnItemSelectedListener(object : OnItemSelectedListener {
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
                            commonSpinnerSelectedItemListener.selectedItem(Office())
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

    }


    fun setPayscale(
        spinner: Spinner,
        context: Context?,
        dataList: List<Paysacle>?,
        payScaleId: Int?,
        commonSpinnerSelectedItemListener: CommonSpinnerSelectedItemListener
    ) {
        var preparence: MySharedPreparence? = context?.let { MySharedPreparence(it) }
        var list = ArrayList<String?>()
        list.add(context?.getString(R.string.select_option))
        var selectedPosition = 0
        var i = 0
        dataList?.let {
            while (i < it.size) {
                if (preparence?.getLanguage().equals("en"))
                    list.add(dataList.get(i).amount)
                else
                    list.add(dataList.get(i).amount)
                payScaleId?.let { it2 ->
                    if (it2==it.get(i).id) {
                        selectedPosition = i + 1
                    }
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
                    if (position <= it.size && position > 0)
                        commonSpinnerSelectedItemListener.selectedItem(dataList?.get(position - 1))
                    else {
                        if (position == 0)
                            commonSpinnerSelectedItemListener.selectedItem(Paysacle())
                    }
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
        Log.e("datalist", "data : " + name)
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
                if (it.get(i).name != null && it.get(i).name.equals("" + name, ignoreCase = true)) {
                    selectedPosition = i + 1
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
        spinner.setOnItemSelectedListener(object : OnItemSelectedListener {
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
                            commonSpinnerSelectedItemListener.selectedItem(SpinnerDataModel())
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

    }


}