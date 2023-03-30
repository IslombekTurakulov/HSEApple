package com.iuturakulov.data.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iuturakulov.data.dao.ChatDao
import com.iuturakulov.data.dao.CourseDao
import com.iuturakulov.domain.entities.ChatEntity

@Database(entities = [ChatEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun chatDao(): ChatDao
    abstract fun courseDao(): CourseDao
}