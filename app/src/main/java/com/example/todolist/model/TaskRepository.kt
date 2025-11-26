package com.example.todolist.model

object TaskRepository {

    private val taskList = mutableListOf<Task>()

    fun getAllTasks(): List<Task> = taskList

    fun addTask(task: Task) {
        taskList.add(task)
    }

    fun updateTask(updatedTask: Task) {
        val index = taskList.indexOfFirst { it.id == updatedTask.id }
        if (index != -1) {
            taskList[index] = updatedTask
        }
    }

    fun deleteTask(taskId: Int) {
        taskList.removeAll { it.id == taskId }
    }

    fun getTaskById(id: Int): Task? {
        return taskList.find { it.id == id }
    }

    fun getFavoriteTasks(): List<Task> {
        return taskList.filter { it.isFavorite }
    }
}