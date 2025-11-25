package com.example.todolist.data

import android.content.Context
import androidx.room.*
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.*

// TypeConverter untuk Date <-> Long
class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time
}

@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "todolist_app.db"

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context.applicationContext).also { INSTANCE = it }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration() // simple migration during dev
                .build()
    }
}
