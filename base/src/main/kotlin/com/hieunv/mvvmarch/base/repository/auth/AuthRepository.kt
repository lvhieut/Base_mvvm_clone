package com.hieunv.mvvmarch.base.repository.auth

import com.hieunv.mvvmarch.base.api.common.SuccessState
import com.hieunv.mvvmarch.base.api.common.rxjava.Result
import com.hieunv.mvvmarch.base.entity.OauthToken
import io.reactivex.Observable

interface AuthRepository {

    /**
     *  Call login to network
     *
     *  @param email (required)
     *  @param password (required)
     **/
    fun login(
            email: String,
            password: String
    ): Observable<Result<OauthToken>>

    /**
     *  Get Oauth Token from Shared Preferences.
     *
     **/
    fun getOauthToken(): OauthToken?

    /**
     *  Call logout to network
     *
     **/
    fun logout(): Observable<Result<SuccessState>>

}
