package com.example.todolist.model

object TaskRepository {

    private val taskList = mutableListOf<Task>()

    init {
        addDummyData()
    }

    private fun addDummyData() {
        taskList.add(
            Task(
                id = "1",
                title = "Pemrograman Mobile",
                description = "Membuat Project Besar Ujian Akhir Semester",
                time = "1:35 PM",
                date = "12 Jan 2025",
                subtasks = mutableListOf(
                    SubTask(text = "UI/UX Design", isDone = false),
                    SubTask(text = "Implementasi ke Aplikasi", isDone = false)
                ),
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
                date = "12 Jan 2025",
                subtasks = mutableListOf(),
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
                date = "13 Jan 2025",
                subtasks = mutableListOf(
                    SubTask(text = "Belajar Linear Regression", isDone = true)
                ),
                isDone = true,
                isFavorite = false
            )
        )
    }

    fun addTask(task: Task) {
        taskList.add(task)
    }

    fun getAllTasks(): MutableList<Task> {
        return taskList
    }

    fun getTaskById(id: String): Task? {
        return taskList.find { it.id == id }
    }

    fun updateTask(updated: Task) {
        val index = taskList.indexOfFirst { it.id == updated.id }
        if (index != -1) taskList[index] = updated
    }

    fun deleteTask(id: String) {
        taskList.removeIf { it.id == id }
    }

    fun setFavorite(id: String, state: Boolean) {
        taskList.find { it.id == id }?.isFavorite = state
    }

    fun getFavorites(): MutableList<Task> {
        return taskList.filter { it.isFavorite }.toMutableList()
    }

    fun setDone(id: String, state: Boolean) {
        taskList.find { it.id == id }?.isDone = state
    }

    fun getCompletedTaskCount(): Int {
        return taskList.count { it.isDone }
    }

    fun getPendingTaskCount(): Int {
        return taskList.count { !it.isDone }
    }

    fun clearAll() {
        taskList.clear()
        addDummyData()
    }
}