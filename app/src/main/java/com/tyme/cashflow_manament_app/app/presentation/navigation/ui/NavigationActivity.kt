package com.tyme.cashflow_manament_app.app.presentation.navigation.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.navigation.NavigationView
import com.tyme.base.Common.FragmentEnum
import com.tyme.base.presentation.activity.BaseActivity
import com.tyme.cashflow_manament_app.R
import com.tyme.cashflow_manament_app.databinding.ActivityNavigationBinding
import com.tyme.feature_dashboard.presentation.ui.DashboardFragment
import com.tyme.base.ext.FragmentListener
import com.tyme.feature_account.presentation.ui.AccountFragment
import com.tyme.feature_transaction.presentation.ui.TransactionHistoryActivity


class NavigationActivity : BaseActivity(R.layout.activity_navigation), FragmentListener {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        // Initialize Binding
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(DashboardFragment())

        drawerLayout = binding.parentContainer
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.account -> {
                    replaceFragment(AccountFragment())
//                startActivity(Intent(this, AccountFragment::class.java))
                }
                R.id.history -> {
                    startActivity(Intent(this, TransactionHistoryActivity::class.java))
                }
                R.id.budget -> {
                    startActivity(Intent(this, TransactionHistoryActivity::class.java))
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    // Callback navigation from fragment
    override fun onNavigate(fragment: FragmentEnum) {
        when (fragment) {
            FragmentEnum.Dashboard -> replaceFragment(DashboardFragment())
            FragmentEnum.Account -> replaceFragment(AccountFragment())
            FragmentEnum.Transaction -> {
                val intent = Intent(this, TransactionHistoryActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        navController = Navigation.findNavController(this, R.id.navHostFragment)
//        return NavigationUI.navigateUp(navController, drawerLayout)
//    }


    // Method to change fragment
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            com.tyme.base.R.anim.slide_in,
            com.tyme.base.R.anim.slide_out
        )
        fragmentTransaction.replace(R.id.navHostFragment, fragment)
        fragmentTransaction.commit()
    }

}