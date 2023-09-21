package com.hieunv.mvvmarch.sample.platform

import com.facebook.stetho.Stetho
import com.hieunv.mvvmarch.base.platform.BaseApplication
import com.hieunv.mvvmarch.base.util.DevUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SampleApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        initStetho()
    }

    private fun initStetho() {
        if (!DevUtils.isRunningTests()) {
            Stetho.initializeWithDefaults(this)
        }
    }

}
