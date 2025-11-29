package com.example.todolist.model

object TaskRepository {

    private val taskList = mutableListOf<Task>()

    // Tambah task
    fun addTask(task: Task) {
        taskList.add(task)
    }

    // Ambil semua task
    fun getAllTasks(): MutableList<Task> {
        return taskList
    }

    // Ambil task berdasarkan ID
    fun getTaskById(id: String): Task? {
        return taskList.find { it.id == id }
    }

    // Update task
    fun updateTask(updated: Task) {
        val index = taskList.indexOfFirst { it.id == updated.id }
        if (index != -1) {
            taskList[index] = updated
        }
    }

    // Hapus task
    fun deleteTask(id: String) {
        val index = taskList.indexOfFirst { it.id == id }
        if (index != -1) taskList.removeAt(index)
    }

    // Favorite/unfavorite
    fun setFavorite(id: String, state: Boolean) {
        val task = getTaskById(id)
        task?.isFavorite = state
    }

    // Ambil yang difavoritkan
    fun getFavorites(): MutableList<Task> {
        return taskList.filter { it.isFavorite }.toMutableList()
    }

    // Set status selesai / belum
    fun setDone(id: String, state: Boolean) {
        val task = getTaskById(id)
        task?.isDone = state
    }
}