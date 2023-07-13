package com.tyme.cashflow_manament_app.app

import android.app.Application
import com.google.android.material.color.DynamicColors
import com.tyme.base.baseModule
import com.tyme.cashflow_manament_app.BuildConfig
import com.tyme.cashflow_manament_app.appModule
import com.tyme.feature_account.featureAccountModule
import com.tyme.feature_dashboard.featureDashboardModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initTimber()
        initDynamicColorScheme()
    }

    private fun initDynamicColorScheme() {
        DynamicColors.applyToActivitiesIfAvailable(this)
    }

    private fun initKoin() {
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@MainApplication)

            modules(appModule)
            modules(baseModule)
            modules(featureAccountModule)
            modules(featureDashboardModule)
            modules(authenticationModule)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
