plugins {
    alias(libs.plugins.android.application) // Usando alias do TOML
    id("com.google.gms.google-services") // Outros plugins necessários
}


android {
    namespace = "com.example.kidelicia"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.kidelicia"
        minSdk = 28
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

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //dependencia para o banco de dados
    implementation(platform(libs.firebase.bom))

    implementation(libs.firebase.analytics)
}