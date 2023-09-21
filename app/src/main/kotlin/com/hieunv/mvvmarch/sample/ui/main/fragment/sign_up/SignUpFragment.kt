package com.hieunv.mvvmarch.sample.ui.main.fragment.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.hieunv.mvvmarch.sample.R
import com.hieunv.mvvmarch.sample.databinding.FragmentSignUpBinding
import com.hieunv.mvvmarch.sample.ui.home.HomeActivity
import com.hieunv.mvvmarch.sample.ui.splash.SplashViewModel

class SignUpFragment : Fragment() {

    private lateinit var signUpBinding: FragmentSignUpBinding
    private val signUpViewModel: SignUpViewModel by viewModels()
    private val splashViewModel: SplashViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        signUpBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        return signUpBinding.root
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

        signUpBinding.apply {
            btnContinue.setOnClickListener {
                activity?.let { splashActivity ->
                    Navigation.findNavController(splashActivity, R.id.nav_host_home_fragment)
                        .navigate(R.id.openMain)
                    splashViewModel.setFirstLogin(false)
                }
            }
        }
    }
}
