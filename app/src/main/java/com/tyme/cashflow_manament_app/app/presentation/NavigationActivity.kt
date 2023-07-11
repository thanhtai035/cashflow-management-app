package com.tyme.cashflow_manament_app.app.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tyme.base.Common.FragmentEnum
import com.tyme.base.presentation.activity.BaseActivity
import com.tyme.cashflow_manament_app.R
import com.tyme.cashflow_manament_app.databinding.ActivityNavigationBinding
import com.tyme.feature_dashboard.presentation.ui.DashboardFragment
import com.tyme.base.ext.FragmentListener
import com.tyme.feature_account.presentation.ui.AccountFragment


class NavigationActivity : BaseActivity(R.layout.activity_navigation), FragmentListener {

    private val binding: ActivityNavigationBinding by viewBinding()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        replaceFragment(DashboardFragment())
    }

    override fun onNavigate(fragment: FragmentEnum) {
        when(fragment) {
            FragmentEnum.Dashboard -> replaceFragment(DashboardFragment())
            FragmentEnum.Account -> replaceFragment(AccountFragment())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(com.tyme.base.R.anim.slide_in, com.tyme.base.R.anim.slide_out)
        fragmentTransaction.replace(R.id.navHostFragment, fragment)
        fragmentTransaction.commit()
    }


}