package com.hieunv.mvvmarch.base.api.user

import com.hieunv.mvvmarch.base.util.rx.RxSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteDataSource @Inject constructor(
        private val userApi: UserApi,
        private val rxSchedulers: RxSchedulers
) {

}
