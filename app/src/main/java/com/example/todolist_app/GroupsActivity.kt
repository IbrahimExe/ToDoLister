package com.example.todolist_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GroupsActivity : AppCompatActivity()
{
    lateinit var newGroupButton: Button
    lateinit var groupsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.groups_layout)

        AppData.initializeGroups()

        newGroupButton = findViewById(R.id.newGroupButton_id)
        groupsRecyclerView = findViewById(R.id.groupsRecyclerView_id)

        groupsRecyclerView.layoutManager = LinearLayoutManager(this)
        groupsRecyclerView.adapter = GroupsAdapter()
    }
}

class GroupViewHolder (rootLayout: LinearLayout) : RecyclerView.ViewHolder (rootLayout)
{
    val groupNameTextView = rootLayout.findViewById<TextView>(R.id.groupNameTextView_id)
    val groupCountTextView = rootLayout.findViewById<TextView>(R.id.groupCountTextView_id)
}

class GroupsAdapter () : RecyclerView.Adapter<GroupViewHolder>()
{
    override fun getItemCount(): Int = AppData.groups.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder
    {
        val rootLinearLayout = LayoutInflater.from(parent.context)
                              .inflate(R.layout.group_row, parent, false)
                              as LinearLayout

        return GroupViewHolder(rootLinearLayout)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int)
    {
        val thisGroup = AppData.groups[position]
        val thisGroupsName = thisGroup.name
        val thisGroupsCount:Int = thisGroup.tasks.size

        holder.groupNameTextView.text = thisGroupsName
        holder.groupCountTextView.text = "$thisGroupsCount Items Left!"
    }
}

data class Task (val name: String, val isCompleted: Boolean)

data class Group (val name: String, var tasks: MutableList<Task>)

class AppData
{
    companion object
    {
        val groups = mutableListOf<Group>()

        fun initializeGroups ()
        {
            val task1 = Task("Task 1", false)
            val task2 = Task("Task 2", false)
            val task3 = Task("Task 3", false)
            val task4 = Task("Task 4", false)
            val task5 = Task("Task 5", false)

            val group1 = Group("Party Bookings", mutableListOf(task1, task2, task3))
            val group2 = Group("Groceries", mutableListOf(task4, task5))

            groups.add(group1)
            groups.add(group2)
        }
    }
}