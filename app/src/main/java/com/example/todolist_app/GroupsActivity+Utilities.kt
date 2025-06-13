package com.example.todolist_app

import android.app.AlertDialog
import android.graphics.Color
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager


fun GroupsActivity.cacheOutlets ()
{
    groupsRecyclerView = findViewById(R.id.groupsRecyclerView_id)
    groupsRecyclerView.layoutManager = LinearLayoutManager(this)
    groupsRecyclerView.adapter = GroupAdapter(this)

    newGroupButton = findViewById(R.id.newGroupButton_id)
    newGroupButton.setOnClickListener (createNewGroup())
}

fun GroupsActivity.createNewGroup(): View.OnClickListener
{
    return View.OnClickListener {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add a New Group")
        builder.setMessage("Enter the name of your new group")

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val padding = (16 * resources.displayMetrics.density).toInt()
        layout.setPadding(padding, padding, padding, padding)

        val input = EditText(this)
        val warningText = TextView(this).apply {
            setTextColor(Color.GRAY)
            text = "Only letters and numbers allowed! ( Max 20 characters )"
            visibility = View.GONE
        }

        val maxLength = 20
        layout.addView(input)
        layout.addView(warningText)
        builder.setView(layout)

        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }

        builder.setPositiveButton("Add") { _, _ ->
            val groupName = input.text.toString().trim()

            when {
                groupName.isEmpty() -> {
                    Toast.makeText(this, "Group name cannot be empty!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                groupName.length > maxLength -> {
                    Toast.makeText(
                        this,
                        "Group name must be under $maxLength characters!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setPositiveButton
                }

                !groupName.matches(Regex("^[a-zA-Z0-9 ]+$")) -> {
                    Toast.makeText(this, "Only letters and numbers allowed!", Toast.LENGTH_SHORT)
                        .show()
                    return@setPositiveButton
                }

                AppData.groups.any { it.name.equals(groupName, ignoreCase = true) } -> {
                    Toast.makeText(this, "Group name already exists!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                else -> {
                    val newGroup = Group(groupName)
                    AppData.groups.add(newGroup)
                    groupsRecyclerView.adapter?.notifyItemInserted(AppData.groups.size - 1)
                }
            }
        }

        val dialog = builder.create()
        dialog.show()

        // Live validation display
        input.addTextChangedListener (object : android.text.TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString()
                warningText.visibility =
                    if (text.length > maxLength || !text.matches(Regex("^[a-zA-Z0-9 ]*$")))
                        View.VISIBLE else View.GONE
            }

            override fun afterTextChanged(s: android.text.Editable?) {}
        } )
    }
}