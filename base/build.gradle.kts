@Suppress("DSL_SCOPE_VIOLATION") // Because of IDE bug https://youtrack.jetbrains.com/issue/KTIJ-19370
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.symbolProcessing)
    alias(libs.plugins.safeArgs)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.testLogger)
    alias(libs.plugins.junit5Android)
}

android {
    namespace = "com.tyme.base"

    compileSdk = 33

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    api(libs.kotlin)
    api(libs.playCore)
    api(libs.coreKtx)
    api(libs.fragmentKtx)
    api(libs.viewBindingPropertyDelegate)
    api(libs.timber)
    api(libs.constraintLayout)
    api(libs.appCompat)
    api(libs.recyclerView)
    api(libs.coroutines)
    api(libs.material)
    api(libs.composeMaterial)
    api(libs.slidinguppanel)
    api(libs.glide)
    api(libs.accompanistFlowLayout)
    api(libs.circleimage)
    api(libs.progressview)
    api(libs.shimmer)
    api(libs.percentagechartview)
    api(libs.progressbar)
    api(libs.piechart)
    api(libs.dialog)
    api(libs.mpchart)
    api(libs.dotindicator)
    api(libs.spinkit)

    api(libs.bundles.koin)
    api(libs.bundles.retrofit)
    api(libs.bundles.navigation)
    api(libs.bundles.lifecycle)
    api(libs.bundles.compose)

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.junitJupiterEngine)
}
