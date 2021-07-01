package com.dss.hrms.view.report.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.dss.hrms.R
import com.dss.hrms.databinding.FragmentPdfViewerBinding
import com.dss.hrms.util.FilePath
import com.joanzapata.pdfview.listener.OnLoadCompleteListener
import com.joanzapata.pdfview.listener.OnPageChangeListener
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_report.*
import java.io.File


class PdfViewerFragment : DaggerFragment(), OnLoadCompleteListener, OnPageChangeListener {
    private val args by navArgs<PdfViewerFragmentArgs>()

    lateinit var binding: FragmentPdfViewerBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.toolBar!!.title = args?.title
        view?.let {
            Navigation.findNavController(it)
                .getCurrentDestination()?.setLabel("Hello")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPdfViewerBinding.inflate(inflater, container, false)


        activity?.let {
            FilePath().getPath(
                it,
                Uri.parse(args.filePath)
            )?.let {
                binding.pdfview.fromFile(File(it))
                    //  .defaultPage(2)
                    //  .showMinimap(true)
                    //   .enableSwipe(true)
                    .onLoad(this)
                    //   .onPageChange(this)
                    .load()
            }
        }
        return binding.root
    }

    override fun loadComplete(nbPages: Int) {

    }

    override fun onPageChanged(page: Int, pageCount: Int) {

    }

}