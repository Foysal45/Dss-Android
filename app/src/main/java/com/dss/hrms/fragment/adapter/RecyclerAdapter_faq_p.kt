package com.dss.hrms.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.R
import com.dss.hrms.fragment.model.BoxParent
import kotlinx.android.synthetic.main.item_show_card.view.*


class RecyclerAdapter_faq_p(var mcontext: Context, var mData: MutableList<BoxParent>) :
    RecyclerView.Adapter<RecyclerAdapter_faq_p.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View
        view = LayoutInflater.from(mcontext).inflate(R.layout.item_show_card, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.rec_child_p.layoutManager = LinearLayoutManager(mcontext)
        holder.rec_child_p.adapter = RecyclerAdapter_box(mcontext, mData[position].getChild())

    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rec_child_p: RecyclerView
        init {
            rec_child_p = itemView.rec_child
        }
    }

    init {
        this.mData = mData
    }
}