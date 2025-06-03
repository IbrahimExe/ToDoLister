package com.example.todolist_app

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TasksActivity : AppCompatActivity()
{
    lateinit var selectedGroupNameTextView: TextView
    lateinit var goToGroupsButton: Button

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

    }
}