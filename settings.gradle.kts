pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MusicApp"
include(":app")
include(":core:ui")
include(":core:data")
include(":feature:login")
include(":feature:home")
include(":core:common")
include(":feature:AlbumList")
include(":feature:PlayList")
include(":feature:search")
include(":feature:artist")
include(":core:player")
