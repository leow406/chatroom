buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Ici vous pouvez ajouter d'autres dépendances au besoin
    }
}

plugins {
    alias(libs.plugins.android.application) apply false  // Plugin Android
    alias(libs.plugins.google.gms.google.services) apply false  // Google Services
}
