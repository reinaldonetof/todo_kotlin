package com.example.todolist.datasource

import com.example.todolist.model.Task

object TaskDataSource {
    private val list = arrayListOf<Task>()

    fun getList() = list.sortedBy { it.timestamp }

    fun insertTask(task: Task) {
        if(task.id == 0 ) {
            list.add(task.copy(id = list.size + 1))
            return
        }
        list.remove(task)
        list.add(task)
    }

    fun findById(taskId: Int) = list.find { it.id == taskId }

    fun deleteTask(task: Task) {
        list.remove(task)
    }
}