import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class MusicAndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jetbrains.kotlin.kapt")
        pluginManager.apply("com.google.dagger.hilt.android")

        val libs = libs()
        dependencies {
            add("implementation", libs.findLibrary("hilt-android").get())
            add("kapt", libs.findLibrary("hilt-compiler").get())
        }
    }
}
