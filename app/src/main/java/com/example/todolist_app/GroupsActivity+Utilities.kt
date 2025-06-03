package com.example.todolist_app

import android.app.AlertDialog
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager


fun GroupsActivity.cacheOutlets ()
{
    groupsRecyclerView = findViewById(R.id.groupsRecyclerView_id)
    groupsRecyclerView.layoutManager = LinearLayoutManager(this)
    groupsRecyclerView.adapter = GroupAdapter(this)

    newGroupButton = findViewById(R.id.newGroupButton_id)
    newGroupButton.setOnClickListener (createNewGroup())

}

fun GroupsActivity.createNewGroup () : View.OnClickListener
{
    return View.OnClickListener ()
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add a New Group")
        builder.setMessage("Enter the name of your new group")

        // Need input validation guard rails
        val input = EditText(this)
        builder.setView(input)

        builder.setNegativeButton("Cancel") { _, _ -> }

        builder.setPositiveButton("Add") { _, _ ->
            val groupName = input.text.toString()
            val newGroup = Group(groupName)
            AppData.groups.add(newGroup)
            groupsRecyclerView.adapter?.notifyItemInserted(AppData.groups.size - 1)
        }

        builder.create().show()
    }
}