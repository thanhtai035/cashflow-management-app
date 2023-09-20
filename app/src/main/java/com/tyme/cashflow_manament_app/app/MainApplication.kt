package com.tyme.cashflow_manament_app.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.color.DynamicColors
import com.tyme.base.baseModule
import com.tyme.cashflow_manament_app.BuildConfig
import com.tyme.cashflow_manament_app.appModule
import com.tyme.feature_account.featureAccountModule
import com.tyme.feature_chatbot.featureChatBotModule
import com.tyme.feature_dashboard.featureDashboardModule
import com.tyme.feature_notification.featureNotificationModule
import com.tyme.feature_transaction.featureTransactionModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import timber.log.Timber

// Set up modules
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initTimber()
        initDynamicColorScheme()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

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
            modules(featureTransactionModule)
            modules(featureAccountModule)
            modules(featureDashboardModule)
            modules(featureChatBotModule)
            modules(authenticationModule)
            modules(featureNotificationModule)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
