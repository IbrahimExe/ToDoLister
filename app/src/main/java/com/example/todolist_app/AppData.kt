package com.example.todolist_app

class AppData
{
    companion object
    {
        var groups = mutableListOf<Group>()

        fun initializeGroups ()
        {
            val task1 = Task("Task 1", )
            val task2 = Task("Task 2", )
            val task3 = Task("Task 3", true)
            val task4 = Task("Task 4", )
            val task5 = Task("Task 5", false)

            val group1 = Group("Party Bookings", mutableListOf(task1, task2, task3))
            val group2 = Group("Groceries", mutableListOf(task4, task5))
            val group3 = Group("Homework", mutableListOf())

            groups = mutableListOf(group1, group2, group3)
        }
    }
}
