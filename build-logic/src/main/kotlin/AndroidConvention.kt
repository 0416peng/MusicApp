import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureAndroid(
    extension: CommonExtension<*, *, *, *, *, *>
) {
    extension.apply {
        compileSdk = 36

        defaultConfig {
            minSdk = 26
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
}

internal fun Project.configureCompose(
    extension: CommonExtension<*, *, *, *, *, *>
) {
    pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

    extension.buildFeatures {
        compose = true
    }

    val libs = libs()
    dependencies {
        add("implementation", platform(libs.findLibrary("androidx-compose-bom").get()))
        add("androidTestImplementation", platform(libs.findLibrary("androidx-compose-bom").get()))
        add("implementation", libs.findLibrary("androidx-compose-ui").get())
        add("implementation", libs.findLibrary("androidx-compose-ui-graphics").get())
        add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
        add("implementation", libs.findLibrary("androidx-compose-material3").get())
        add("androidTestImplementation", libs.findLibrary("androidx-compose-ui-test-junit4").get())
        add("debugImplementation", libs.findLibrary("androidx-compose-ui-test-manifest").get())
        add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
    }
}

internal fun Project.addCommonAndroidDependencies() {
    val libs = libs()
    dependencies {
        add("implementation", libs.findLibrary("androidx-core-ktx").get())
        add("implementation", libs.findLibrary("androidx-appcompat").get())
        add("implementation", libs.findLibrary("material").get())
        add("testImplementation", libs.findLibrary("junit").get())
        add("androidTestImplementation", libs.findLibrary("androidx-junit").get())
        add("androidTestImplementation", libs.findLibrary("androidx-espresso-core").get())
    }
}

internal fun Project.libs(): VersionCatalog =
    extensions.getByType<VersionCatalogsExtension>().named("libs")
