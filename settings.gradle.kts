import org.gradle.kotlin.dsl.accessors.runtime.addDependencyTo

pluginManagement {
    repositories {

        maven {
            url = uri("https://artifact.bytedance.com/repository/pangle")
        }

        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {

        maven {
            url = uri("https://artifact.bytedance.com/repository/pangle")
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "P-Gromore"
include(":app")
include(":gromore")
