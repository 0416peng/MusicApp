plugins {
    id("music.android.library.compose")
    id("music.android.hilt")
}

android {
    namespace = "com.example.home"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:ui"))

    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.coil.compose)
}
