package com.hieunv.mvvmarch.base.db.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import com.hieunv.mvvmarch.base.entity.User

@Dao
abstract class UserDao {

    @Insert
    abstract fun insert(user: User)

    @Update
    abstract fun updateItem(user: User)

}
