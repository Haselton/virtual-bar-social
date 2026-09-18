plugins { id("com.android.application"); id("org.jetbrains.kotlin.android") }
android {
    namespace = "com.haseltonmediagroup.virtualbarsocial"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.haseltonmediagroup.virtualbarsocial"
        minSdk = 26
        targetSdk = 35
        versionCode = 2
        versionName = "0.1.1-prototype"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
}