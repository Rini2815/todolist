package com.example.todolist.model

object TaskRepository {
    private val taskList = mutableListOf<Task>()

    init {
        // Dummy data untuk testing
        addDummyData()
    }

    private fun addDummyData() {
        taskList.add(
            Task(
                id = "1",
                title = "Pemrograman Mobile",
                description = "Membuat Project Besar Ujian Akhir Semester",
                time = "1:35 PM",
                isDone = false,
                isFavorite = false
            )
        )
        taskList.add(
            Task(
                id = "2",
                title = "UI/UX Design",
                description = "Mendesain aplikasi to-do list",
                time = "1:00 PM",
                isDone = false,
                isFavorite = true
            )
        )
        taskList.add(
            Task(
                id = "3",
                title = "Sistem Cerdas",
                description = "Belajar machine learning",
                time = "7:00 AM",
                isDone = true,
                isFavorite = false
            )
        )
    }

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
        taskList.removeIf { it.id == id }
    }

    // Toggle favorite
    fun setFavorite(id: String, state: Boolean) {
        taskList.find { it.id == id }?.isFavorite = state
    }

    // Ambil yang difavoritkan
    fun getFavorites(): MutableList<Task> {
        return taskList.filter { it.isFavorite }.toMutableList()
    }

    // Toggle status selesai
    fun setDone(id: String, state: Boolean) {
        taskList.find { it.id == id }?.isDone = state
    }

    // Clear all (untuk testing/logout)
    fun clearAll() {
        taskList.clear()
        addDummyData()
    }
}