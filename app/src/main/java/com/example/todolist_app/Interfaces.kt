package com.example.todolist_app

import androidx.recyclerview.widget.RecyclerView

interface OnGroupClickedListener
{
    fun onGroupClicked (position: Int)
    {

    }

    fun onGroupLongClicked (position: Int)
    {

    }

    fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
    {

    }

}