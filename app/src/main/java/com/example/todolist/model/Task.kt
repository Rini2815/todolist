package com.example.todolist.model

data class Task(
    val id: String = System.currentTimeMillis().toString(),   // Auto generate ID
    var title: String = "",
    var description: String = "",
    var time: String = "",
    var date: String = "",
    var isDone: Boolean = false,
    var isFavorite: Boolean = false
)
