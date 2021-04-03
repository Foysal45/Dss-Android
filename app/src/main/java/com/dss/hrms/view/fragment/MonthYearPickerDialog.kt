package com.dss.hrms.view.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogMonthYearPickerBinding
import com.dss.hrms.view.allInterface.OnDateListener
import com.dss.hrms.view.allInterface.OnYearMonthListener
import java.util.*

class MonthYearPickerDialog(val date: Date = Date(), val onYearMonthListener: OnYearMonthListener) :
    DialogFragment() {

    companion object {
        private const val MAX_YEAR = 2099
    }

    lateinit var binding: DialogMonthYearPickerBinding

    private var listener: DatePickerDialog.OnDateSetListener? = null

    fun setListener(listener: DatePickerDialog.OnDateSetListener?) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogMonthYearPickerBinding.inflate(requireActivity().layoutInflater)
        val cal: Calendar = Calendar.getInstance().apply { time = date }
        val selectedCal: Calendar = Calendar.getInstance().apply { time = Date() }

        binding.pickerMonth.run {
            minValue = 0
            maxValue = 11
            value = cal.get(Calendar.MONTH)
            displayedValues = arrayOf(
                "Jan", "Feb", "Mar", "Apr", "May", "June", "July",
                "Aug", "Sep", "Oct", "Nov", "Dec"
            )
        }

        binding.pickerYear.run {
            val year = cal.get(Calendar.YEAR)
            val selectYear = selectedCal.get(Calendar.YEAR)
            minValue = year
            maxValue = MAX_YEAR
            value = selectYear
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(context?.getString(R.string.please_select_month_and_year))
            .setView(binding.root)
            .setPositiveButton("Ok") { _, _ ->
                listener?.onDateSet(
                    null,
                    binding.pickerYear.value,
                    binding.pickerMonth.value + 1,
                    1
                )
                onYearMonthListener.onYearMonth(
                    binding.pickerYear.value.toString(),
                    (binding.pickerMonth.value + 1).toString()
                )
            }
            .setNegativeButton("Cancel") { _, _ -> dialog?.cancel() }
            .create()
    }
}