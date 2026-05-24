plugins {
    id("music.android.library.compose")
    id("music.android.hilt")
}

android {
    namespace = "com.example.ui"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))

    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.coil.compose)
}
