package com.hieunv.mvvmarch.sample.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.hieunv.mvvmarch.base.platform.AppManager
import com.hieunv.mvvmarch.sample.R
import com.hieunv.mvvmarch.sample.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    companion object {

        private const val TAG = "HomeActivity"

    }

    @Inject
    lateinit var appManager: AppManager

    private lateinit var homeBinding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        initiateToolbar()
    }

    private fun initiateToolbar() {
        setSupportActionBar(homeBinding.homeToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowCustomEnabled(true)

        homeBinding.actionBackBtn.setOnClickListener {
            onBackPressed()
        }
    }

    fun updateToolbar() {
        val navController = Navigation.findNavController(this, R.id.nav_host_home_fragment)

        when (navController.currentDestination?.id) {
            R.id.settingLanguageFragment -> {
                homeBinding.apply {
                    homeToolbarTitle.text = "Setting Language"
                    homeAppBar.visibility = View.VISIBLE
                    actionBackBtn.visibility = View.GONE
                }
            }
            R.id.onBoardingFragment -> {
                homeBinding.apply {
                    homeAppBar.visibility = View.GONE
                }
            }
            R.id.signUpFragment -> {
                homeBinding.apply {
                    homeAppBar.visibility = View.VISIBLE
                    homeToolbarTitle.text = "Sign Up"
                    actionBackBtn.visibility = View.VISIBLE
                }

            }
        }
    }

    override fun onNavigateUp(): Boolean =
        findNavController(R.id.nav_host_home_fragment).navigateUp()

    override fun onBackPressed() {
        val navController = Navigation.findNavController(this, R.id.nav_host_home_fragment)
        if (navController.currentDestination?.id != R.id.settingLanguageFragment) {
            navController.popBackStack()
        } else {
            if (appManager.isBackPressFinish) {
                finish()
            } else {
                Toast.makeText(this, R.string.back_pressed_finish, Toast.LENGTH_SHORT).show()
            }
        }
    }

}
