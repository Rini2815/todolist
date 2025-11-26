package com.example.todolist.model

data class Task(
    val id: Int,
    var title: String,
    var description: String,
    var date: String,
    var time: String,
    var isFavorite: Boolean = false,
    var isCompleted: Boolean = false
)