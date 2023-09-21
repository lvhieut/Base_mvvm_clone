package com.hieunv.mvvmarch.base.api.auth

import com.hieunv.mvvmarch.base.api.auth.request.LoginRequest
import com.hieunv.mvvmarch.base.api.auth.request.LogoutRequest
import com.hieunv.mvvmarch.base.api.common.SuccessState
import com.hieunv.mvvmarch.base.api.common.rxjava.Result
import com.hieunv.mvvmarch.base.api.common.rxjava.retrofitResultResponseToResult
import com.hieunv.mvvmarch.base.entity.OauthToken
import com.hieunv.mvvmarch.base.util.rx.RxSchedulers
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRemoteDataSource @Inject constructor(
        private val authApi: AuthApi,
        private val rxSchedulers: RxSchedulers
) {

    fun login(email: String, password: String): Observable<Result<OauthToken>> {
        return authApi.login(LoginRequest(email,password))
                .retrofitResultResponseToResult()
                .subscribeOn(rxSchedulers.io())
                .observeOn(rxSchedulers.androidThread())
    }

    fun logout(fcmToken: String): Observable<Result<SuccessState>> {
        return authApi.logout(LogoutRequest(fcmToken))
                .retrofitResultResponseToResult()
                .subscribeOn(rxSchedulers.io())
                .observeOn(rxSchedulers.androidThread())
    }
}
