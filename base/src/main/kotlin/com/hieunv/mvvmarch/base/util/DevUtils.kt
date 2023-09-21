package com.hieunv.mvvmarch.base.util

import com.hieunv.mvvmarch.base.BuildConfig
import java.util.concurrent.atomic.AtomicBoolean

object DevUtils {

    private var isRunningUnitTest: AtomicBoolean? = null
    private var isRunningInstrumentedTest: AtomicBoolean? = null

    fun isLoggable(): Boolean {
        return BuildConfig.DEBUG && !DevUtils.isRunningTests()
    }

    fun isRunningTests(): Boolean {
        return DevUtils.isRunningUnitTest() || DevUtils.isRunningInstrumentedTest()
    }

    @Synchronized
    fun isRunningUnitTest(): Boolean {
        if (DevUtils.isRunningUnitTest == null) {
            var isRunningUnitTest = false

            try {
                Class.forName("org.robolectric.RobolectricTestRunner")
                isRunningUnitTest = true
            } catch (e: ClassNotFoundException) {
                // Ignored
            }

            DevUtils.isRunningUnitTest = AtomicBoolean(isRunningUnitTest)
        }

        return DevUtils.isRunningUnitTest?.get() ?: false
    }

    @Synchronized
    fun isRunningInstrumentedTest(): Boolean {
        if (DevUtils.isRunningInstrumentedTest == null) {
            var isRunningInstrumentedTest = false

            try {
                Class.forName("android.support.test.espresso.Espresso")
                isRunningInstrumentedTest = true
            } catch (e: ClassNotFoundException) {
                // Ignored
            }

            DevUtils.isRunningInstrumentedTest = AtomicBoolean(isRunningInstrumentedTest)
        }

        return DevUtils.isRunningInstrumentedTest?.get() ?: false
    }

}
