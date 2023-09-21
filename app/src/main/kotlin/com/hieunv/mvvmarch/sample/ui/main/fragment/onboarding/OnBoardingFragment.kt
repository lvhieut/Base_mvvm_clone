package com.hieunv.mvvmarch.sample.ui.main.fragment.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.hieunv.mvvmarch.sample.R
import com.hieunv.mvvmarch.sample.databinding.FragmentOnBoardingBinding
import com.hieunv.mvvmarch.sample.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : Fragment() {

    private lateinit var onBoardingBinding: FragmentOnBoardingBinding
    private val onBoardingViewModel: OnBoardingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onBoardingBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_on_boarding, container, false)
        return onBoardingBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as HomeActivity).updateToolbar()
        initiateView()
    }

    private fun initiateView() {
        activity?.let {
            it.window.statusBarColor = ContextCompat.getColor(it, R.color.white)
        }

        onBoardingBinding.apply {
            btnContinue.setOnClickListener {
                activity?.let { homeActivity ->
                    Navigation.findNavController(homeActivity, R.id.nav_host_home_fragment)
                        .navigate(R.id.openSignUp)
                }
            }
        }

    }
}
