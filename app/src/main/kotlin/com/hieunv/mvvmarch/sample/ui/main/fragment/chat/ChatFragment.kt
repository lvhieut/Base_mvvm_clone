package com.hieunv.mvvmarch.sample.ui.main.fragment.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hieunv.mvvmarch.sample.R
import com.hieunv.mvvmarch.sample.databinding.FragmentChatBinding
import com.hieunv.mvvmarch.sample.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private lateinit var chatBinding: FragmentChatBinding
    private val chatViewModel: ChatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        chatBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        return chatBinding.root
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

        chatBinding.apply {

        }

    }
}
