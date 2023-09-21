package com.hieunv.mvvmarch.sample.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.hieunv.mvvmarch.base.preferences.SharedPrefsApi

class SplashViewModel @ViewModelInject constructor(
    private val sharedPrefsApi: SharedPrefsApi
) : ViewModel() {

    private enum class Keys {
        FIST_LOGIN,
    }

    fun setFirstLogin(isFirstLogin: Boolean) {
        sharedPrefsApi.set(Keys.FIST_LOGIN.name, isFirstLogin)
    }

    fun getFirstLogin(): Boolean {
        return sharedPrefsApi.get(Keys.FIST_LOGIN.name, true)
    }

}
