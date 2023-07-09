package com.tyme.feature_dashboard.presentation.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.tyme.feature_dashboard.R
import com.tyme.feature_dashboard.databinding.AnalysisModalBinding
import com.tyme.feature_dashboard.databinding.FragmentDashboardBinding

class ModalFragment : DialogFragment(R.layout.analysis_modal) {
    private lateinit var binding: AnalysisModalBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = AnalysisModalBinding.inflate(layoutInflater) //initializing the binding class
        // Set the dialog fragment's style to remove the default title and background
        setStyle(STYLE_NO_TITLE, 0)

        // Inflate the custom layout for the dialog
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        // Set the background of the dialog fragment to a transparent color to achieve the blurred effect
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val speedScale = 1000000f
        binding.savingProgress.apply {
            setAnimationSpeedScale(speedScale)
            setProgress(85)
        }

        binding.spendingProgress.apply {
            setAnimationSpeedScale(speedScale)
            setProgress(75)
        }

        binding.complianceProgress.apply {
            setAnimationSpeedScale(speedScale)
            setProgress(85)
        }

        binding.investProgress.apply {
            setAnimationSpeedScale(speedScale)
            setProgress(80)
        }

        binding.debtProgress.apply {
            setAnimationSpeedScale(speedScale)
            setProgress(90)
        }

    }
}