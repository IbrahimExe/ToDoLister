package com.example.todolist_app

data class Task (val name: String,
                 val isCompleted: Boolean = false)

data class Group (val name: String,
                  var tasks: MutableList<Task> = mutableListOf())