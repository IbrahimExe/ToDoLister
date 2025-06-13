package com.example.todolist_app

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TasksActivity : AppCompatActivity() {

    lateinit var selectedGroupNameTextView: TextView
    lateinit var goToGroupsButton: Button
    lateinit var addTaskButton: Button
    lateinit var taskRecyclerView: RecyclerView

    lateinit var group: Group
    lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.tasks_layout)

        selectedGroupNameTextView = findViewById(R.id.selectedGroupNameTextView_id)
        goToGroupsButton = findViewById(R.id.goToGroupsButton_id)
        addTaskButton = findViewById(R.id.addTaskButton_id)
        taskRecyclerView = findViewById(R.id.taskRecyclerView_id)

        val position = intent.getIntExtra("Position", 0)
        group = AppData.groups[position]
        selectedGroupNameTextView.text = group.name

        taskRecyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(group)
        taskRecyclerView.adapter = taskAdapter

        goToGroupsButton.setOnClickListener {
            finish()
        }

        // Show add task dialog when button is clicked
        addTaskButton.setOnClickListener {
            showAddTaskDialog { taskName ->
                val newTask = Task(taskName, false)
                group.tasks.add(newTask)
                taskAdapter.notifyItemInserted(group.tasks.size - 1)
                taskRecyclerView.scrollToPosition(group.tasks.size - 1)
            }

            fun showAddTaskDialog() {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Add a New Task")
                builder.setMessage("Enter the name of your new task")

                val input = EditText(this)
                val padding = (16 * resources.displayMetrics.density).toInt()
                input.setPadding(padding, padding, padding, padding)
                builder.setView(input)

                builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }

                builder.setPositiveButton("Add") { _, _ ->
                    val taskName = input.text.toString().trim()

                    when {
                        taskName.isEmpty() -> {
                            Toast.makeText(this, "Task name cannot be empty!", Toast.LENGTH_SHORT)
                                .show()
                        }

                        taskName.length > 20 -> {
                            Toast.makeText(
                                this,
                                "Task name must be under 20 characters!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        !taskName.matches(Regex("^[a-zA-Z0-9 ]+$")) -> {
                            Toast.makeText(
                                this,
                                "Only letters and numbers allowed!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {
                            val newTask = Task(taskName, false)
                            group.tasks.add(newTask)
                            taskAdapter.notifyItemInserted(group.tasks.size - 1)
                            taskRecyclerView.scrollToPosition(group.tasks.size - 1)
                        }
                    }
                }
                builder.show()
            }
        }
    }
}

class TaskViewHolder(rootLayout: LinearLayout) : RecyclerView.ViewHolder(rootLayout) {
    val taskNameTextView = rootLayout.findViewById<TextView>(R.id.taskNameTextView_id)
    val taskCheckBox = rootLayout.findViewById<CheckBox>(R.id.taskCheckBox_id)

    fun bind(task: Task) {
        taskNameTextView.text = task.name
        taskCheckBox.isChecked = task.isCompleted
    }
}

class TaskAdapter(val group: Group) : RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val rootLinearLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_row, parent, false)
                as LinearLayout

        return TaskViewHolder(rootLinearLayout)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(group.tasks[position])
    }

    override fun getItemCount(): Int = group.tasks.size
}