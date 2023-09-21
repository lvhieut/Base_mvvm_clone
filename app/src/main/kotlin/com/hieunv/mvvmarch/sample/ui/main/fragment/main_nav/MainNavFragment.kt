package com.hieunv.mvvmarch.sample.ui.main.fragment.main_nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.hieunv.mvvmarch.sample.R
import com.hieunv.mvvmarch.sample.databinding.FragmentMainNavBinding
import com.hieunv.mvvmarch.sample.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainNavFragment : Fragment() {

    private lateinit var mainNavBinding: FragmentMainNavBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainNavBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main_nav, container, false)
        return mainNavBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as MainActivity).updateToolbar()
        initiateView()
    }

    private fun initiateView() {
        activity?.let {
            it.window.statusBarColor = ContextCompat.getColor(it, R.color.white)
        }

        mainNavBinding.apply {
            btnAddFriends.setOnClickListener {
                NavHostFragment.findNavController(this@MainNavFragment).navigate(R.id.goToAddFriend)
            }
            btnChat.setOnClickListener {
                NavHostFragment.findNavController(this@MainNavFragment).navigate(R.id.goToChat)
            }
            btnProfile.setOnClickListener {
                NavHostFragment.findNavController(this@MainNavFragment).navigate(R.id.goToProfile)
            }
            btnFriends.setOnClickListener {
                NavHostFragment.findNavController(this@MainNavFragment).navigate(R.id.goToFriends)
            }
            btnSearch.setOnClickListener {
                NavHostFragment.findNavController(this@MainNavFragment).navigate(R.id.goToSearch)
            }
        }
    }
}
