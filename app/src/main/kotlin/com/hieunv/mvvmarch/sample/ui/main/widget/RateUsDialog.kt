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
import com.hieunv.mvvmarch.sample.databinding.LayoutDialogRateUsBinding
import kotlin.math.min

class RateUsDialog(private val listener: OnConfirmListener) : DialogFragment() {
    private var binding: LayoutDialogRateUsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutDialogRateUsBinding.inflate(inflater, container, false)
        isCancelable = false
        setupEvent()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val screenHeight = Resources.getSystem().displayMetrics.heightPixels
        val dialogWidth = min(screenWidth, screenHeight) * 0.9f
        dialog?.window?.setLayout(dialogWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun show(manager: FragmentManager, tag: String?) {
        manager.beginTransaction().add(this, tag).commitAllowingStateLoss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setupEvent() {
        binding?.btnRate?.setOnClickListener {
            listener.onClickRate()
            dismissAllowingStateLoss()
        }
        binding?.btnCancel?.setOnClickListener {
            listener.onClickCancel()
            dismissAllowingStateLoss()
        }
    }

    interface OnConfirmListener {
        fun onClickRate()
        fun onClickCancel()
    }
}