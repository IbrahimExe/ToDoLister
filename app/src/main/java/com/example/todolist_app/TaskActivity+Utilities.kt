package com.example.todolist_app

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.graphics.Color
import android.view.View
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast

// Function to create and show the Add Task dialog with validation callbacks
fun Context.showAddTaskDialog(onTaskAdded: (String) -> Unit) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle("Add a New Task")
    builder.setMessage("Enter the name of your new task")

    val layout = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        val padding = (16 * resources.displayMetrics.density).toInt()
        setPadding(padding, padding, padding, padding)
    }

    val input = EditText(this)
    val warningText = TextView(this).apply {
        setTextColor(Color.GRAY)
        text = "Only letters and numbers allowed!\n( Max 20 characters )"
        visibility = View.GONE
    }

    layout.addView(input)
    layout.addView(warningText)
    builder.setView(layout)

    val maxLength = 20

    // Live validation listener
    input.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val text = s.toString()
            warningText.visibility = if (text.length > maxLength || !text.matches(Regex("^[a-zA-Z0-9 ]*$"))) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
        override fun afterTextChanged(s: Editable?) {}
    })

    builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
    builder.setPositiveButton("Add") { dialog, _ ->
        val taskName = input.text.toString().trim()

        when {
            taskName.isEmpty() -> {
                Toast.makeText(this, "Task name cannot be empty!", Toast.LENGTH_SHORT).show()
            }
            taskName.length > maxLength -> {
                Toast.makeText(this, "Task name must be under $maxLength characters!", Toast.LENGTH_SHORT).show()
            }
            !taskName.matches(Regex("^[a-zA-Z0-9 ]+$")) -> {
                Toast.makeText(this, "Only letters and numbers allowed!", Toast.LENGTH_SHORT).show()
            }
            else -> {
                onTaskAdded(taskName)
            }
        }
    }

    builder.show()
}
