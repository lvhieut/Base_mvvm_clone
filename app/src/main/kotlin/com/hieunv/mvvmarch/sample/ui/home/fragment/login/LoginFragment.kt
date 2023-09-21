package com.hieunv.mvvmarch.sample.ui.home.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.hieunv.mvvmarch.sample.R
import com.hieunv.mvvmarch.sample.databinding.FragmentLoginBinding
import com.hieunv.mvvmarch.sample.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var loginBinding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        loginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return loginBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as HomeActivity).updateToolbar()

        initiateView()
        handleObservable()

        loginBinding.loginBtn.setOnClickListener {
            val email = loginBinding.emailEdt.text.toString()
            val password = loginBinding.passwordEdt.text.toString()

            loginViewModel.login(email, password)
        }
    }

    private fun initiateView() {
        loginBinding.viewModel = loginViewModel
    }

    private fun handleObservable() {
        loginViewModel.getSuccessLogin().observe(
                viewLifecycleOwner,
                Observer {
                    NavHostFragment.findNavController(this).navigate(R.id.openMain)
                    (activity as HomeActivity).finish()
                }
        )

        loginViewModel.getErrorLogin().observe(
                viewLifecycleOwner,
                Observer {
                    Toast.makeText(activity, R.string.login_fail, Toast.LENGTH_SHORT).show()
                }
        )
    }

}
