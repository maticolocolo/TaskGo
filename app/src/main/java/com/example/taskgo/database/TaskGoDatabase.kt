package com.example.taskgo.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TareaEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TaskGoDatabase : RoomDatabase() {

    abstract fun tareaDao(): TareaDao
}
