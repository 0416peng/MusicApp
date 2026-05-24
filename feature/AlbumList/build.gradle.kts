plugins {
    id("music.android.library.compose")
    id("music.android.hilt")
}

android {
    namespace = "com.example.albumlist"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:player"))
    implementation(project(":core:ui"))

    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.coil.compose)
}
