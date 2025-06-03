package com.example.todolist_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TasksActivity : AppCompatActivity()
{
    lateinit var selectedGroupNameTextView: TextView
    lateinit var goToGroupsButton: Button
    lateinit var taskRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.tasks_layout)

        selectedGroupNameTextView = findViewById(R.id.selectedGroupNameTextView_id)

        val position = intent.getIntExtra("Position", 0)
        val group = AppData.groups[position]
        val name = group.name
        selectedGroupNameTextView.text = name


        goToGroupsButton = findViewById(R.id.goToGroupsButton_id)
        goToGroupsButton.setOnClickListener {
            finish()
        }

        taskRecyclerView = findViewById(R.id.taskRecyclerView_id)
        taskRecyclerView.layoutManager = LinearLayoutManager(this)
        taskRecyclerView.adapter = TaskAdapter(group)

    }
}

class TaskViewHolder (rootLayout: LinearLayout) : RecyclerView.ViewHolder (rootLayout)
{
    val taskNameTextView = rootLayout.findViewById<TextView>(R.id.taskNameTextView_id)
    val taskCheckBox = rootLayout.findViewById<CheckBox>(R.id.taskCheckBox_id)

    fun bind(task: Task)
    {
        taskNameTextView.text = task.name
        taskCheckBox.isChecked = task.isCompleted
    }
}

class TaskAdapter (val group: Group) : RecyclerView.Adapter<TaskViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder
    {
        val rootLinearLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_row, parent, false)
                as LinearLayout

        return TaskViewHolder (rootLinearLayout)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int)
    {
        holder.bind(group.tasks[position])
    }

    override fun getItemCount(): Int = group.tasks.size
}