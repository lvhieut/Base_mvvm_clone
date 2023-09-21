package com.hieunv.mvvmarch.sample.ui.splash.fragment

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hieunv.mvvmarch.sample.R
import com.hieunv.mvvmarch.sample.databinding.FragmentSplashNavBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashNavFragment : Fragment() {

    private lateinit var splashNavBinding: FragmentSplashNavBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        splashNavBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash_nav, container, false)
        return splashNavBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
