package com.tyme.cashflow_manament_app.app.presentation.authentication

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import com.tyme.base.presentation.activity.BaseActivity
import com.tyme.base_feature.common.Result
import com.tyme.cashflow_manament_app.R
import com.tyme.cashflow_manament_app.app.presentation.navigation.ui.NavigationActivity
import com.tyme.cashflow_manament_app.app.presentation.authentication.util.AuthenticationDialogEnum
import com.tyme.cashflow_manament_app.databinding.ActivityAuthenticationMigrationBinding
import com.tyme.cashflow_manament_app.databinding.ActivityAuthenticationViewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthenticationActivity : BaseActivity(R.layout.activity_authentication_view) {
    private val viewModel: AuthenticationViewModel by viewModel()
    private lateinit var binding: ActivityAuthenticationMigrationBinding
    private lateinit var modalDialogFragment: AuthenticationDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Push edit text programmatically
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        binding = ActivityAuthenticationMigrationBinding.inflate(layoutInflater) //initializing the binding class
        setContentView(binding.root)

        // Set card based on screen width heihgt
        val screenHeight = Resources.getSystem().displayMetrics.heightPixels
        val paramCard = binding.imageView2.layoutParams
        paramCard.width = (screenHeight * 0.35).toInt()
        paramCard.height = (screenHeight * 0.35).toInt()

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
                        Log.d("taitest",response.message?:"")
                        // Case: Valid credential
                        if (response.data?.success?:false) {
                            Log.d("taitest","login")
                            modalDialogFragment.cancelDialog()
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
                        modalDialogFragment.updateDialogState(AuthenticationDialogEnum.Error)
                    }, 3000)
                }
            }
        }
        super.onStart()
    }
}