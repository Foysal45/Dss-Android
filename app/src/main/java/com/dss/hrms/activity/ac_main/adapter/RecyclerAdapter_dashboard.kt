package com.chaadride.fragment.fg_hubList.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.R
import com.dss.hrms.activity.ac_main.model.Dashboard
import kotlinx.android.synthetic.main.item_dashboard.view.*


class RecyclerAdapter_dashboard(var mcontext: Context, var mData: MutableList<Dashboard>) :
    RecyclerView.Adapter<RecyclerAdapter_dashboard.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View
        view = LayoutInflater.from(mcontext).inflate(R.layout.item_dashboard, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.dashboad_menu_title.text = mData[position].getDashBoardName()
        holder.dashboad_menu_icon.setImageResource(mData[position].getDashBoardImage())

    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dashboad_menu_title: TextView
        val dashboad_menu_icon: ImageView
        init {
            dashboad_menu_title = itemView.dashboad_menu_title
            dashboad_menu_icon = itemView.dashboad_menu_icon
        }
    }

    init {
        this.mData = mData
    }
}