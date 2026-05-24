plugins {
    id("music.android.library")
    id("music.android.hilt")
}

android {
    namespace = "com.example.core.player"
}

dependencies {
    implementation(project(":core:data"))

    implementation(libs.androidx.media)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.session)
    implementation(libs.androidx.media3.ui)
    implementation(libs.kotlinx.coroutines.android)
}
