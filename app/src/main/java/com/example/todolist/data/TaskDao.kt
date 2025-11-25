// /app/src/main/java/com/todolist/data/TaskDao.kt
package com.todolist.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks ORDER BY dueDate IS NULL, dueDate ASC")
    fun getAllTasksFlow(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    suspend fun getTaskById(id: String): Task?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tasks: List<Task>)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteById(id: String)

    // queries for UI convenience
    @Query("SELECT * FROM tasks WHERE favorite = 1 ORDER BY dueDate ASC")
    fun getFavoritesFlow(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE done = 1 ORDER BY dueDate DESC")
    fun getCompletedFlow(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE done = 0 ORDER BY dueDate ASC")
    fun getPendingFlow(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE date(dueDate/1000, 'unixepoch') = date(:day/1000, 'unixepoch') ORDER BY dueDate ASC")
    fun getTasksByDayFlow(day: Long): Flow<List<Task>>
}
