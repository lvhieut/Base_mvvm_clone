package com.hieunv.mvvmarch.sample.ui.home.fragment.login

import android.util.Log
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.hieunv.mvvmarch.base.api.common.ErrorState
import com.hieunv.mvvmarch.base.api.common.rxjava.RetrofitObserver
import com.hieunv.mvvmarch.base.entity.OauthToken
import com.hieunv.mvvmarch.base.repository.auth.AuthRepository
import com.hieunv.mvvmarch.base.ui.SingleLiveData
import com.hieunv.mvvmarch.base.util.extension.plusAssign
import io.reactivex.disposables.CompositeDisposable

class LoginViewModel @ViewModelInject constructor(
        private val authRepository: AuthRepository
) : ViewModel() {

    companion object {
        private const val TAG = "LoginViewModel"
    }

    private val compositeDisposable = CompositeDisposable()

    private val successLogin = SingleLiveData<OauthToken>()
    private val errorLogin = SingleLiveData<ErrorState>()

    val isLoading = ObservableField(false)

    fun getSuccessLogin(): SingleLiveData<OauthToken> {
        return successLogin
    }

    fun getErrorLogin(): SingleLiveData<ErrorState> {
        return errorLogin
    }

    fun login(
            email: String,
            password: String
    ) {
        isLoading.set(true)
        compositeDisposable += authRepository
                .login(email, password)
                .subscribeWith(LoginObserver())
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    private inner class LoginObserver : RetrofitObserver<OauthToken>() {
        override fun onSuccess(t: OauthToken) {
            Log.d(TAG, "LoginObserver: onSuccess - $t")
            isLoading.set(false)
            successLogin.postValue(t)
        }

        override fun onError(error: ErrorState) {
            Log.d(TAG, "LoginObserver: onError - $error")
            isLoading.set(false)
            errorLogin.postValue(error)
        }
    }

}
