// /app/src/main/java/com/todolist/data/Task.kt
package com.todolist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

@Entity(tableName = "tasks")
@TypeConverters(DateConverter::class)
data class Task(
    @PrimaryKey val id: String,          // use timestamp or UUID as id
    val title: String,
    val description: String = "",
    val dueDate: Date? = null,           // nullable, converter will handle
    val reminder: Boolean = false,       // toggle pengingat
    val favorite: Boolean = false,
    val done: Boolean = false
)
