package com.hieunv.mvvmarch.base.repository.user

import com.hieunv.mvvmarch.base.api.user.UserRemoteDataSource
import com.hieunv.mvvmarch.base.db.user.UserLocalDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
        private val userRemoteDataSource: UserRemoteDataSource,
        private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

}
