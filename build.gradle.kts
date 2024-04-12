// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
buildscript {
    dependencies {
        //Gromore自动拉取 Adapre AAR 插件脚本
        classpath("com.pangle.cn:mediation-auto-adapter:1.0.3")
    }
}


plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
}

true // Needed to make the Suppress annotation work for the plugins block