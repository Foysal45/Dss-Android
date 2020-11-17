package com.dss.hrms.view.adapter.employeeInfo


import android.content.Context
import android.text.InputType
import android.util.TypedValue
import android.widget.EditText
import android.widget.LinearLayout
import com.dss.hrms.R
import com.google.android.material.textfield.TextInputLayout


class CustomeView {

//
//    fun createEdittext(context: Context) {
//
//        val linearLayoutSubParent = LinearLayout(context)
//        linearLayoutSubParent.orientation = LinearLayout.HORIZONTAL
//        linearLayoutSubParent.weightSum = 100f // you can also add more widget by providing weight
//
//
//        val linearLayoutSubParentParams = LinearLayout.LayoutParams(
//            0,
//            LinearLayout.LayoutParams.WRAP_CONTENT, 100f
//        )
//        linearLayoutSubParent.layoutParams = linearLayoutSubParentParams
//        linearLayoutSubParent.setPadding(0, 0, 0, 0)
//
//        // Add TextInputLayout to parent layout first
//
//        // Add TextInputLayout to parent layout first
//        val textInputLayout = TextInputLayout(this)
//        val textInputLayoutParams = LinearLayout.LayoutParams(
//            0,
//            LinearLayout.LayoutParams.WRAP_CONTENT, 100f
//        )
//
//
//        textInputLayout.layoutParams = textInputLayoutParams
//        textInputLayout.setHintTextAppearance(R.style.TextSizeHint)
//        linearLayoutSubParent.addView(textInputLayout)
//
//        // Add EditText control to TextInputLayout
//
//        // Add EditText control to TextInputLayout
//        val editText = EditText(this)
//        val editTextParams = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
//        )
//        editTextParams.setMargins(0, 10, 0, 0)
//        editText.layoutParams = editTextParams
//
//        editText.setTextSize(
//            TypedValue.COMPLEX_UNIT_PX,
//            getResources().getDimension(R.dimen.text_size)
//        )
//        editText.hint = "Enter value"
//        editText.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
//        editText.isEnabled = false
//
//        textInputLayout.addView(editText, editTextParams)
//
//
//        linearLayoutParent.addView(linearLayoutSubParent)
//
//    }


    fun getEditextText(context: Context, hint: String): EditText {
        val editText = EditText(context)
        val editTextParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            context.resources.getDimensionPixelSize(R.dimen.edittext_height_extra_large)
        )
        editTextParams.setMargins(0, 10, 0, 10)
        editText.layoutParams = editTextParams
        editText.setPadding(10, 0, 0, 0)

        editText.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            context.resources.getDimension(R.dimen.text_size_medium)
        )
        editText.hint = hint
        editText.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
        editText.isEnabled = false
        return editText
    }
}