package com.hieunv.mvvmarch.sample.ui.main.fragment.setting_language

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
import com.hieunv.mvvmarch.sample.databinding.FragmentSettingLanguageBinding
import com.hieunv.mvvmarch.sample.ui.home.HomeActivity

class SettingLanguageFragment : Fragment() {
    companion object {
        private const val TAG = "SettingLanguageFragment"
    }

    private lateinit var settingLanguageBinding: FragmentSettingLanguageBinding

    private val settingLanguageViewModel: SettingLanguageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        settingLanguageBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_setting_language, container, false)
        return settingLanguageBinding.root
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

        settingLanguageBinding.btnContinue.setOnClickListener {
            activity?.let { homeActivity ->
                Navigation.findNavController(homeActivity, R.id.nav_host_home_fragment)
                    .navigate(R.id.openOnBoarding)
            }
        }
    }

}