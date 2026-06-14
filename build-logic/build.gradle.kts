plugins {
    `kotlin-dsl`
}

group = "com.example.musicapp.buildlogic"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.hilt.android.gradle.plugin)
    implementation(libs.kotlin.compose.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "music.android.application"
            implementationClass = "MusicAndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "music.android.library"
            implementationClass = "MusicAndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "music.android.library.compose"
            implementationClass = "MusicAndroidLibraryComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "music.android.hilt"
            implementationClass = "MusicAndroidHiltConventionPlugin"
        }
    }
}
