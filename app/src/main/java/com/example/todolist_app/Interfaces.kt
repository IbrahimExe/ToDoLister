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
}

interface OnTaskClickedListener
{
    fun onTaskClicked (position: Int)
    {

    }

    fun onTaskLongClicked (position: Int)
    {

    }
}