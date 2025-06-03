package com.example.todolist_app

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class GroupViewHolder (rootLayout: LinearLayout) : RecyclerView.ViewHolder (rootLayout)
{
    val groupNameTextView = rootLayout.findViewById<TextView>(R.id.groupNameTextView_id)
    val groupCountTextView = rootLayout.findViewById<TextView>(R.id.groupCountTextView_id)

    fun bind(group: Group) {
        groupNameTextView.text = group.name
        groupCountTextView.text = "${group.tasks.size} Task(s) Left!"
    }
}

class GroupAdapter (val listener: OnGroupClickedListener) : RecyclerView.Adapter<GroupViewHolder>()
{
    override fun getItemCount(): Int = AppData.groups.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val rootLinearLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.group_row, parent, false)
                as LinearLayout

        return GroupViewHolder(rootLinearLayout)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val thisGroup = AppData.groups[position]
        holder.bind(thisGroup)

        holder.itemView.setOnClickListener {
            // Do something when clicked
            listener.onGroupClicked(position) // Sending the message
        }

        holder.itemView.setOnLongClickListener {
            // Do something when long clicked
            listener.onGroupLongClicked(position)
            true
        }


    }

}
