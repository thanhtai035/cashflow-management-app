package com.tyme.feature_dashboard.presentation.ui

import android.content.res.Resources
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

    companion object {
        private const val KEY_SPENDING = "key_spending"
        private const val KEY_SAVING = "key_saving"
        private const val KEY_INVESTMENT = "key_investment"
        private const val KEY_COMPLIANCE = "key_compliance"
        private const val KEY_STRING = "key_string"

        fun newInstance(int1: Int, int2: Int, int3: Int, int4: Int, stringArg: String): ModalFragment {
            val args = Bundle().apply {
                putInt(KEY_SPENDING, int1)
                putInt(KEY_SAVING, int2)
                putInt(KEY_INVESTMENT, int3)
                putInt(KEY_COMPLIANCE, int4)
                putString(KEY_STRING, stringArg)
            }
            val fragment = ModalFragment()
            fragment.arguments = args
            return fragment
        }

        private fun putInt(spending: Int, int1: Int) {

        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = AnalysisModalBinding.inflate(layoutInflater) //initializing the binding class
        // Set the dialog fragment's style to remove the default title and background
        val screenHeight = Resources.getSystem().displayMetrics.heightPixels
        val params = binding.modalLayout.layoutParams
        params.height = (screenHeight * 0.9).toInt()
        binding.modalLayout.layoutParams = params
        // Inflate the custom layout for the dialog
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val spending = arguments?.getInt(KEY_SPENDING) ?: 0
        val saving = arguments?.getInt(KEY_SAVING) ?: 0
        val investment = arguments?.getInt(KEY_INVESTMENT) ?: 0
        val compliance = arguments?.getInt(KEY_COMPLIANCE) ?: 0
        var string = arguments?.getString(KEY_STRING) ?: ""
        // Set the background of the dialog fragment to a transparent color to achieve the blurred effect
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val speedScale = 1000000f
        binding.savingProgress.apply {
            setAnimationSpeedScale(speedScale)
            setProgress(saving * 10)
        }

        binding.spendingProgress.apply {
            setAnimationSpeedScale(speedScale)
            setProgress(spending * 10)
        }

        binding.complianceProgress.apply {
            setAnimationSpeedScale(speedScale)
            setProgress(compliance * 10)
        }

        binding.investProgress.apply {
            setAnimationSpeedScale(speedScale)
            setProgress(investment * 10)
        }
        binding.analysisContent.text = string

    }
}