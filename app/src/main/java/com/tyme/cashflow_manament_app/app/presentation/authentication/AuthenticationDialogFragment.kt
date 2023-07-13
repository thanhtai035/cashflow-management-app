package com.tyme.cashflow_manament_app.app.presentation.authentication

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.tyme.cashflow_manament_app.R
import com.tyme.cashflow_manament_app.app.presentation.authentication.util.AuthenticationDialogEnum
import com.tyme.cashflow_manament_app.databinding.AuthenticationDialogBinding


class AuthenticationDialogFragment : DialogFragment(R.layout.authentication_dialog) {
    private lateinit var binding: AuthenticationDialogBinding

    companion object {
        private const val ARG_ENUM = "arg_message"

        public fun newInstance(enumValue: AuthenticationDialogEnum): AuthenticationDialogFragment {
            val fragment = AuthenticationDialogFragment()
            val args = Bundle()
            args.putSerializable(ARG_ENUM, enumValue)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var enumValue: AuthenticationDialogEnum

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = AuthenticationDialogBinding.inflate(layoutInflater) //initializing the binding class
        // Set the dialog fragment's style to remove the default title and background
        setStyle(STYLE_NO_TITLE, 0)
        enumValue = arguments?.getSerializable(ARG_ENUM) as AuthenticationDialogEnum

        // Inflate the custom layout for the dialog
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        // Set the background of the dialog fragment to a transparent color to achieve the blurred effect
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        updateView()
    }

    fun updateDialogState(newValue: AuthenticationDialogEnum) {
        enumValue = newValue
        updateView()
    }

    fun updateView() {
        when(enumValue) {
            AuthenticationDialogEnum.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
                binding.errorLayout.visibility = View.GONE
                binding.invalidLayout.visibility = View.GONE
                dialog?.setCanceledOnTouchOutside(false) // Prevent dialog from being dismissed outside
            }
            AuthenticationDialogEnum.Error -> {
                binding.loadingLayout.visibility = View.GONE
                binding.errorLayout.visibility = View.VISIBLE
                binding.invalidLayout.visibility = View.GONE
                dialog?.setCanceledOnTouchOutside(true)
            } else -> {
            binding.loadingLayout.visibility = View.GONE
            binding.errorLayout.visibility = View.INVISIBLE
            binding.invalidLayout.visibility = View.VISIBLE
            dialog?.setCanceledOnTouchOutside(true)
        }
        }
    }
}