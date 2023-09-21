package com.hieunv.mvvmarch.base.platform

import android.content.Context
import android.net.ConnectivityManager
import android.os.SystemClock
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppManager @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        private const val THRESHOLD_DOUBLE_TIME = 500

        private const val THRESHOLD_FINISH_TIME = 2000
    }

    private var lastClickTime = 0L

    private var backPressedTime = 0L

    val isConnectedToInternet: Boolean
        get() {
            val conManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager

            val ni = conManager.activeNetworkInfo
            return ni != null && ni.isConnected
        }

    val isPreventingDoubleClick: Boolean
        get() {
            // preventing double, using threshold of 500 ms
            if (SystemClock.elapsedRealtime() - lastClickTime < THRESHOLD_DOUBLE_TIME) {
                return true
            }

            lastClickTime = SystemClock.elapsedRealtime()
            return false
        }

    val isBackPressFinish: Boolean
        get() {
            // preventing finish, using threshold of 2000 ms
            if (backPressedTime + THRESHOLD_FINISH_TIME > SystemClock.elapsedRealtime()) {
                return true
            }

            backPressedTime = SystemClock.elapsedRealtime()
            return false
        }

}
