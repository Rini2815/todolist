package com.example.todolist.model

data class Task(
    val id: String,
    var title: String,
    var description: String,
    var time: String,
    var isDone: Boolean = false,
    var isFavorite: Boolean = false
)
