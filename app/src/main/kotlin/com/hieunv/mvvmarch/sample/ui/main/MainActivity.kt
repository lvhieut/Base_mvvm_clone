package com.hieunv.mvvmarch.sample.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.hieunv.mvvmarch.base.platform.AppManager
import com.hieunv.mvvmarch.sample.R
import com.hieunv.mvvmarch.sample.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    @Inject
    lateinit var appManager: AppManager

    private lateinit var mainBinding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initiateToolbar()
    }

    private fun initiateToolbar() {
        setSupportActionBar(mainBinding.mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowCustomEnabled(true)

        mainBinding.actionBackBtn.setOnClickListener {
            onBackPressed()
        }
    }

    fun updateToolbar() {
        val navController = Navigation.findNavController(this, R.id.nav_host_main_fragment)

        when (navController.currentDestination?.id) {
            R.id.mainNavFragment -> {
                mainBinding.mainAppBar.visibility = View.GONE
            }
            R.id.settingFragment, R.id.addFriendFragment, R.id.friendsFragment,
            R.id.chatFragment, R.id.profileFragment, R.id.searchFragment -> {
                mainBinding.apply {
                    mainAppBar.visibility = View.VISIBLE
                    mainToolbarTitle.text = "Screen Title"
                    actionBackBtn.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onBackPressed() {
        val navController = Navigation.findNavController(this, R.id.nav_host_main_fragment)
        if (navController.currentDestination?.id != R.id.mainNavFragment) {
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
