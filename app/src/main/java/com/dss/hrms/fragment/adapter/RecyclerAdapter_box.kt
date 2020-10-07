package com.dss.hrms.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.R
import com.dss.hrms.fragment.model.Box
import kotlinx.android.synthetic.main.item_show_box.view.*


class RecyclerAdapter_box(var mcontext: Context, var mData: MutableList<Box>) :
    RecyclerView.Adapter<RecyclerAdapter_box.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View
        view = LayoutInflater.from(mcontext).inflate(R.layout.item_show_box, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.box_title.text = mData[position].getTitle()
        holder.box_details.hint = mData[position].getHint()
        holder.box_details.text = mData[position].getDescription()
      //  holder.space.visibility = mData[position].getSpaceShow()

    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val box_title: TextView
        val box_details: TextView
       // val space: LinearLayout
        init {
            box_title = itemView.box_title
            box_details = itemView.box_details
            //space = itemView.space
        }
    }

    init {
        this.mData = mData
    }
}