package top.zcwfeng.customui.baseui.view.recyclerview

import android.R
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import top.zcwfeng.customui.baseui.view.TAG

class CustomAdapter(val context: Context, val list: MutableList<String>) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTV: TextView = itemView.findViewById(top.zcwfeng.customui.R.id.tv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val view = LayoutInflater.from(context).inflate(top.zcwfeng.customui.R.layout.item_rv,parent,false)
        Log.e(TAG,"onCreateViewHolder:${itemCount}")
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.mTV.text = list[position]
        Log.e(TAG,"onBindViewHolder$position")
    }
}