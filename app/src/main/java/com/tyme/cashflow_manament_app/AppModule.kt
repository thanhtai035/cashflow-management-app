package com.tyme.cashflow_manament_app


import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tyme.base.Common.Constant
import com.tyme.cashflow_manament_app.app.data.dataModule
import com.tyme.cashflow_manament_app.app.domain.domainModule
import com.tyme.cashflow_manament_app.app.presentation.presentationModule

import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit


val appModule = module {
    presentationModule
    domainModule
    dataModule

//    single { AuthenticationInterceptor(Constant.BEARER_TOKEN) }

    single {
        HttpLoggingInterceptor { message ->
            Timber.d("Http: $message")
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
//            .addInterceptor(get<AuthenticationInterceptor>())
            .build()
    }

    single {
        val contentType = "application/json".toMediaType()

        val json = kotlinx.serialization.json.Json {
            ignoreUnknownKeys = true
        }

        @OptIn(ExperimentalSerializationApi::class)
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(get())
            .client(OkHttpClient.Builder().connectTimeout(40, TimeUnit.SECONDS).build()) // Adjust the timeout as needed
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
}
