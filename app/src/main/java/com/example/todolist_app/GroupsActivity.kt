package com.example.todolist_app

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GroupsActivity : AppCompatActivity(), OnGroupClickedListener
{
    lateinit var newGroupButton: Button
    lateinit var groupsRecyclerView: RecyclerView

    override fun onGroupClicked(position: Int)
    {
        val intent = Intent(this, TasksActivity::class.java)

        intent.putExtra("Position", position)

        startActivity(intent)
    }

    override fun onGroupLongClicked(position: Int)
    {
        // **********************
        // 1) Get the selected Group
        val oldGroup = AppData.groups[position]
        val oldName = oldGroup.name

        // 2) Build AlertDialog for renaming
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Edit Group Name")
        builder.setMessage("Enter a new name for this group")

        // 3) EditText pre-filled with current name
        val input = EditText(this).apply {
            setText(oldName)
            setSelection(oldName.length)
        }

        // 4) Warning TextView (initially GONE)
        val warningText = TextView(this).apply {
            setTextColor(Color.RED)
            text = "Only letters, numbers, and spaces allowed. Max 20 characters."
            visibility = View.GONE
            setPadding(0, 8, 0, 0)
        }

        // 5) Combine into a vertical LinearLayout
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            val pad = (16 * resources.displayMetrics.density).toInt()
            setPadding(pad, pad, pad, pad)
            addView(input)
            addView(warningText)
        }
        builder.setView(layout)

        // 6) “Cancel” button
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        // 7) “Save” button: validate and apply rename
        builder.setPositiveButton("Save") { _, _ ->
            val newName = input.text.toString().trim()
            val maxLength = 20

            when {
                newName.isEmpty() -> {
                    Toast.makeText(this, "Group name cannot be empty.", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                newName.length > maxLength -> {
                    Toast.makeText(this, "Group name must be under $maxLength characters.", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                // Allow letters, numbers, and spaces:
                !newName.matches(Regex("^[a-zA-Z0-9 ]+\$")) -> {
                    Toast.makeText(this, "Only letters, numbers, and spaces allowed.", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                // Check duplicates (ignore case), but skip if it's the same object
                AppData.groups.any {
                    it.name.equals(newName, ignoreCase = true) && it != oldGroup
                } -> {
                    Toast.makeText(this, "That name already exists.", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                else -> {
                    // 8) All good → update the group’s name & notify adapter
                    oldGroup.name = newName
                    groupsRecyclerView.adapter?.notifyItemChanged(position)
                }
            }
        }

        // 9) Show the dialog
        val dialog = builder.create()
        dialog.show()

        // 10) Live validation: show red warning if invalid/too long
        input.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val txt = s.toString()
                // show warning if >20 chars OR contains invalid characters
                warningText.visibility =
                    if (txt.length > 20 || !txt.matches(Regex("^[a-zA-Z0-9 ]*\$")))
                        View.VISIBLE
                    else
                        View.GONE
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })
    }

    private fun setupSwipeToDelete()
    {
        val swipeCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
            {
                val position = viewHolder.adapterPosition
                AppData.groups.removeAt(position)
                groupsRecyclerView.adapter?.notifyItemRemoved(position)
                Toast.makeText(this@GroupsActivity, "Group deleted", Toast.LENGTH_SHORT).show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(groupsRecyclerView)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.groups_layout)

        AppData.initializeGroups()

        cacheOutlets()

        setupSwipeToDelete()
    }
}