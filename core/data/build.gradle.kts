plugins {
    id("music.android.library")
    id("music.android.hilt")
}

android {
    namespace = "com.example.data"
}

dependencies {
    implementation(project(":core:common"))

    implementation(platform(libs.okhttp.bom))
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.tracing.perfetto.handshake)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.core)

    testImplementation(libs.junit.jupiter)
}
