package com.dss.hrms.view.personalinfo.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.request.transition.Transition
import com.dss.hrms.R
import com.dss.hrms.util.ConvertNumber

class name_view_row_adapter(val items: List<String>, val listener: (Int) -> Unit) :
    RecyclerView.Adapter<name_view_row_adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.personel_information_view_field_with_icon, parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position], position, listener)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String, pos: Int, listener: (Int) -> Unit) = with(itemView) {
            val nameTv: TextView = findViewById(R.id.tvText)
            val Icon: ImageView = findViewById(R.id.icon)
            nameTv.setOnClickListener {
                listener(pos)
            }

            ConvertNumber.setIconOnTextView(Icon, nameTv, item, context)

        }
    }
}
