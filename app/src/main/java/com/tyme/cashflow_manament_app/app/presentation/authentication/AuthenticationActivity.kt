package com.tyme.cashflow_manament_app.app.presentation.authentication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import com.tyme.base.presentation.activity.BaseActivity
import com.tyme.base_feature.common.Result
import com.tyme.cashflow_manament_app.R
import com.tyme.cashflow_manament_app.app.presentation.NavigationActivity
import com.tyme.cashflow_manament_app.app.presentation.authentication.util.AuthenticationDialogEnum
import com.tyme.cashflow_manament_app.databinding.ActivityAuthenticationViewBinding
import com.tyme.feature_account.presentation.adapter.TransactionListAdapter
import com.tyme.feature_dashboard.presentation.ui.ModalFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.w3c.dom.Text

class AuthenticationActivity : BaseActivity(R.layout.activity_authentication_view) {
    private val viewModel: AuthenticationViewModel by viewModel()
    private lateinit var binding: ActivityAuthenticationViewBinding
    private lateinit var modalDialogFragment: AuthenticationDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationViewBinding.inflate(layoutInflater) //initializing the binding class
        setContentView(binding.root)

        // Set Log in button listener
        binding.logInBtn.setOnClickListener {
            viewModel.logIn(binding.usernameInput.text.toString(), binding.passwordInput.text.toString())
        }
    }

    override fun onStart() {
        // Check Log in Response
            viewModel.response.observe(this) {
                    response ->
                when (response) {
                    is Result.Success -> {
                        // Case: Valid credential
                        if (response.data?.success?:false) {
                            val intent = Intent(this, NavigationActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        // Case: Invalid input
                        else {
                            Handler().postDelayed({
                                modalDialogFragment.updateDialogState(AuthenticationDialogEnum.Invalid)
                            }, 3000)
                        }
                    }
                        // On Loading - Open dialog with loading progress view
                    is Result.Loading -> {
                        modalDialogFragment = AuthenticationDialogFragment.newInstance(AuthenticationDialogEnum.Loading)
                        modalDialogFragment.show(supportFragmentManager, "modalDialog")
                    }
                        // Error - Change dialog to error message
                    else -> {
                    Handler().postDelayed({
                        Log.d("tai", "123")
                        modalDialogFragment.updateDialogState(AuthenticationDialogEnum.Error)
                    }, 3000)
                }
            }
        }
        super.onStart()
    }
}