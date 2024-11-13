plugins {
    alias(libs.plugins.android.application)  // Utilisation de l'alias du plugin Android
    alias(libs.plugins.google.gms.google.services)  // Utilisation de l'alias pour Google Services
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34  // Reste inchangé

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 23  // Modifié de 21 à 23
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    // Firebase and Room
    implementation(platform(libs.firebase.bom))  // Firebase BoM pour gérer les versions
    implementation(libs.firebase.database)  // Firebase Realtime Database
    implementation(libs.firebase.auth)  // Firebase Authentication
    implementation(libs.room.runtime)  // Room Database
    annotationProcessor(libs.room.compiler)  // Room Database Compiler

    // Autres dépendances
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
