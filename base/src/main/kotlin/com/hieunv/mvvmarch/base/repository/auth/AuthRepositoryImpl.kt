package com.hieunv.mvvmarch.base.repository.auth

import com.hieunv.mvvmarch.base.api.auth.AuthRemoteDataSource
import com.hieunv.mvvmarch.base.api.common.SuccessState
import com.hieunv.mvvmarch.base.api.common.rxjava.Result
import com.hieunv.mvvmarch.base.entity.OauthToken
import com.hieunv.mvvmarch.base.preferences.auth.AuthSharedPrefsDataSource
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
        private val authRemoteDataSource: AuthRemoteDataSource,
        private val authSharedPrefsDataSource: AuthSharedPrefsDataSource
) : AuthRepository {

    override fun login(email: String, password: String): Observable<Result<OauthToken>> {
        return authRemoteDataSource.login(email, password)
                .doOnNext {
                    it.data?.let { authToken ->
                        authSharedPrefsDataSource.saveOauthToken(authToken)
                    }
                }
    }

    override fun getOauthToken(): OauthToken? {
        return authSharedPrefsDataSource.getOauthToken()
    }

    override fun logout(): Observable<Result<SuccessState>> {
        val accessToken = authSharedPrefsDataSource.getAccessToken()
        return authRemoteDataSource.logout(accessToken)
                .doOnNext {
                    authSharedPrefsDataSource.clearOauthToken()
                }
    }

}
