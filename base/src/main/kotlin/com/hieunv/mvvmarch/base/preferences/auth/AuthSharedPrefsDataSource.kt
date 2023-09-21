package com.hieunv.mvvmarch.base.preferences.auth

import com.hieunv.mvvmarch.base.entity.OauthToken
import com.hieunv.mvvmarch.base.preferences.SharedPrefsApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthSharedPrefsDataSource @Inject constructor(
        private val sharedPrefsApi: SharedPrefsApi
) : AuthSharedPrefs {

    private enum class Keys {

        ACCESS_TOKEN,
        REFRESH_TOKEN

    }

    companion object {
        const val DEF_ACCESS_TOKEN = ""
        const val DEF_REFRESH_TOKEN = ""
    }

    fun saveOauthToken(authToken: OauthToken) {
        setAccessToken(authToken.accessToken)
        setRefreshToken(authToken.refreshToken)
    }

    fun getOauthToken(): OauthToken? {
        val accessToken = getAccessToken()
        val refreshToken = getRefreshToken()
        return if (accessToken.isNotEmpty() && refreshToken.isNotEmpty()) {
            OauthToken(accessToken, refreshToken)
        } else null
    }

    fun clearOauthToken() {
        sharedPrefsApi.remove(Keys.ACCESS_TOKEN.name)
        sharedPrefsApi.remove(Keys.REFRESH_TOKEN.name)
    }

    override fun getAccessToken(): String = sharedPrefsApi.get(Keys.ACCESS_TOKEN.name, DEF_ACCESS_TOKEN)

    override fun setAccessToken(accessToken: String) {
        sharedPrefsApi.set(Keys.ACCESS_TOKEN.name, accessToken)
    }

    override fun getRefreshToken(): String = sharedPrefsApi.get(Keys.REFRESH_TOKEN.name, DEF_REFRESH_TOKEN)

    override fun setRefreshToken(refreshToken: String) {
        sharedPrefsApi.set(Keys.REFRESH_TOKEN.name, refreshToken)
    }
}
