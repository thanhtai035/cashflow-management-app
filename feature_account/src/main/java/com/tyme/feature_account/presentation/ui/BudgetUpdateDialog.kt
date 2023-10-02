package com.tyme.feature_account.presentation.ui
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.tyme.feature_account.databinding.BudgetDialogBinding

class BudgetUpdateDialog : DialogFragment() {
    private lateinit var binding: BudgetDialogBinding
    private var dialogListener: NumberDialogListener? = null

    interface NumberDialogListener {
        fun onNumberSelected(number: Double)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BudgetDialogBinding.inflate(inflater, container, false)

        val spendAmount = arguments?.getDouble("arg1", 0.0)?:0.0
        val currentAmount = arguments?.getDouble("arg2", 0.0)
        val category = arguments?.getString("arg3", "")

        binding.currentSpending.text = spendAmount.toString()
        binding.setValue.setText(currentAmount.toString())

        binding.confirmBtn.setOnClickListener {
            if (binding.setValue.text.toString().toDouble() > spendAmount) {
                dialogListener?.onNumberSelected(binding.setValue.text.toString().toDouble())
                dismiss()
            } else {
                binding.warning.visibility = View.VISIBLE
            }
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NumberDialogListener) {
            dialogListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        dialogListener = null
    }
}
