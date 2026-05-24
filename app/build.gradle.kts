plugins {
    id("music.android.application")
    id("music.android.hilt")
}

android {
    namespace = "com.example.musicapp"

    defaultConfig {
        applicationId = "com.example.musicapp"
        versionCode = 1
        versionName = "1.0"
    }

    lint {
        baseline = file("lint-baseline.xml")
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:player"))
    implementation(project(":core:ui"))
    implementation(project(":feature:AlbumList"))
    implementation(project(":feature:PlayList"))
    implementation(project(":feature:artist"))
    implementation(project(":feature:home"))
    implementation(project(":feature:login"))
    implementation(project(":feature:player"))
    implementation(project(":feature:search"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.android)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)
}
