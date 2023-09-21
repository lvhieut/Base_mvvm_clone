package com.hieunv.mvvmarch.sample.ui.main.widget

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.hieunv.mvvmarch.sample.databinding.LayoutLimitDialogBinding
import kotlin.math.min

class LimitTranslateDialog(private val listener: OnConfirmListener) : DialogFragment() {
    private var binding: LayoutLimitDialogBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutLimitDialogBinding.inflate(inflater, container, false)
        isCancelable = false
        setupEvent()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.post {
            val screenWidth = Resources.getSystem().displayMetrics.widthPixels
            val screenHeight = Resources.getSystem().displayMetrics.heightPixels
            val dialogWidth = min(screenWidth, screenHeight) * 0.85f
            dialog?.window?.setLayout(dialogWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        manager.beginTransaction().add(this, tag).commitAllowingStateLoss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setupEvent() {
        binding?.btnWatchAds?.setOnClickListener {
            listener.onClickWatchAds()
            dismissAllowingStateLoss()
        }

        binding?.btnContinue?.setOnClickListener {
            listener.onClickContinue()
            dismissAllowingStateLoss()
        }
    }

    interface OnConfirmListener {
        fun onClickWatchAds()
        fun onClickContinue()
    }
}
