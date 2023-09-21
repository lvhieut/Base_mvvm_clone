package com.hieunv.mvvmarch.sample.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.hieunv.mvvmarch.sample.R
import com.hieunv.mvvmarch.sample.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    companion object {

        private const val TAG = "SplashActivity"

        private const val SPLASH_DISPLAY_LENGTH = 1000L

    }

    private lateinit var splashBinding: ActivitySplashBinding

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        Handler().postDelayed({
            startSampleApplication()
        }, SPLASH_DISPLAY_LENGTH)
    }

    private fun startSampleApplication() {
        val navController = Navigation.findNavController(this, R.id.nav_host_splash_fragment)
//        splashViewModel.getFirstLogin().let {
//            if (it) {
                navController.navigate(R.id.openHome)
//            } else {
//                navController.navigate(R.id.openMain)
//                finish()
//            }
//        }
    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.nav_host_splash_fragment).navigateUp()
}
