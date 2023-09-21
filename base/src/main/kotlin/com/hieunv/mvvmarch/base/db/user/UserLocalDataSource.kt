package com.hieunv.mvvmarch.base.db.user

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalDataSource @Inject constructor(
        private val userDao: UserDao
) {

}
