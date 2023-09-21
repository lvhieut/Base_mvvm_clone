package com.hieunv.mvvmarch.sample.ui.main.fragment.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hieunv.mvvmarch.sample.R
import com.hieunv.mvvmarch.sample.databinding.FragmentSettingBinding
import com.hieunv.mvvmarch.sample.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {

    private lateinit var settingBinding: FragmentSettingBinding
    private val settingViewModel: SettingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        settingBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return settingBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as MainActivity).updateToolbar()

        initiateView()
        handleObservable()
    }

    private fun initiateView() {
        settingBinding.viewModel = settingViewModel
        activity?.let {
            it.window.statusBarColor = getColor(it, R.color.white)
        }
    }

    private fun handleObservable() {
    }

}
