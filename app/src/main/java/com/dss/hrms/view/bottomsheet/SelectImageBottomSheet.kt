package com.dss.hrms.view.bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import com.dss.hrms.R
import com.dss.hrms.databinding.BottomSheetSelectImageBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SelectImageBottomSheet : BottomSheetDialogFragment {

    private var mListener: BottomSheetListener? = null
    private var binding: BottomSheetSelectImageBinding? = null
    private var requestCodeCamera = 0
    private var requestCodeGallery: Int = 0

    constructor(mListener: BottomSheetListener?) {
        this.mListener = mListener
    }

    @SuppressLint("ValidFragment")
    constructor(
        mListener: BottomSheetListener?,
        requestCodeCamera: Int,
        requestCodeGallery: Int
    ) {
        this.mListener = mListener
        this.requestCodeCamera = requestCodeCamera
        this.requestCodeGallery = requestCodeGallery
    }


    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_select_image, container, false)
        binding?.cameraImageL?.setOnClickListener {
            mListener!!.onCameraButtonClicked()
        }
        binding?.galleryImageL?.setOnClickListener {
            mListener!!.onGalleryButtonClicked()
        }
        return binding?.getRoot()
    }


  open  interface BottomSheetListener {
        fun onCameraButtonClicked()
        fun onGalleryButtonClicked()
    }

}