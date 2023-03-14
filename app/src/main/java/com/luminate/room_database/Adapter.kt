package com.luminate.room_database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.luminate.room_database.data.entity.User


class Adapter(var list: List<User>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    private lateinit var dialog: Dialog

    fun setDialog(dialog: Dialog)
    {
        this.dialog = dialog
    }

    interface Dialog {
        fun onClick(position: Int)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        var title : TextView
        var detail : TextView
        init {
            title = view.findViewById(R.id.tv_title)
            detail = view.findViewById(R.id.tv_detail)
            view.setOnClickListener{
                dialog.onClick(layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = list[position].title
        holder.detail.text = list[position].detail
    }

    override fun getItemCount(): Int {
        return list.size
    }
}