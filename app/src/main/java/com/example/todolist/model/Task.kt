package com.example.todolist.model

data class Task(
    val id: String = System.currentTimeMillis().toString(),
    var title: String = "",
    var description: String = "",
    var time: String = "",
    var date: String = "",
    var subtasks: MutableList<SubTask> = mutableListOf(), // Subtask list
    var isDone: Boolean = false,
    var isFavorite: Boolean = false
)

data class SubTask(
    val id: String = java.util.UUID.randomUUID().toString(),
    var text: String,
    var isDone: Boolean = false
)
