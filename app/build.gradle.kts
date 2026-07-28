plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.kirill.projectpad"

    compileSdk = 35

    defaultConfig {
        applicationId = "com.kirill.projectpad"
        minSdk = 26 // ИСПРАВЛЕНО: Позволит ставить приложение на Android 8.0+
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    // ИСПРАВЛЕНО: Добавлен BOM для основного приложения, чтобы версии Compose не конфликтовали
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)

    // НЮАНС: Если вы используете ConstraintLayout ВНУТРИ Compose, раскомментируйте строчку ниже
    // и убедитесь, что добавили "constraintlayout-compose = "1.1.1"" в libs.versions.toml
    // implementation("androidx.constraintlayout:constraintlayout-compose:1.1.1")

    // Если вам нужен обычный XML ConstraintLayout (например для старых фрагментов), оставьте эту:
     implementation(libs.androidx.constraintlayout)
    implementation(libs.core.ktx)

    implementation(libs.gson)
    implementation(libs.okhttp)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
}