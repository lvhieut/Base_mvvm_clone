package com.hieunv.mvvmarch.base.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hieunv.mvvmarch.base.db.user.UserDao
import com.hieunv.mvvmarch.base.entity.User

@Database(entities = [(User::class)],
        version = 1,
        exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}
