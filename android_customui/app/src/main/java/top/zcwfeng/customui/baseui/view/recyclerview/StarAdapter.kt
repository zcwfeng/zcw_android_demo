package top.zcwfeng.customui.baseui.view.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import top.zcwfeng.customui.R
import top.zcwfeng.customui.baseui.view.model.Star


class StarAdapter(val starList: MutableList<Star>, val context: Context) : RecyclerView.Adapter<StarAdapter.StarViewHolder>() {


    // TODO: 2020/7/22 inner class & class ??????
    class StarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv: TextView = itemView.findViewById(R.id.  iv_start)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item_star, null)
        return StarViewHolder(view)
    }

    override fun getItemCount(): Int = starList.size

    override fun onBindViewHolder(holder: StarViewHolder, position: Int) {
        holder.tv.text = starList[position].name
    }

    fun isGroupHeader(position: Int): Boolean {
        return if (position == 0)
            true
        else {
            val curGroupName = getGroupName(position)
            val preGroupName = getGroupName(position -1)
            return preGroupName != curGroupName
        }
    }

    fun getGroupName(position: Int): String {
         return   starList[position].groupName
    }


}