@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.android.library")
    id("mediation-auto-adapter")
    kotlin("android")
}

android {
    namespace = "com.ziguhonglan.plugins.gromore"
    compileSdk = 34

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    //注：adn如果通过aar方式引入，需要把对应的adn aar放到libs目录下，注意aar名称和版本号需要和下面命令行匹配上
    implementation(fileTree("libs").apply {
        include("*.jar")
    })

    implementation(files("libs/open_ad_sdk_6.0.1.4.aar"))
//    implementation("com.pangle.cn:mediation-sdk:6.0.1.4") //穿山甲融合SDK

    //GDT 优量汇 sdk ,通过aar方式引入， 需要把相关aar放到libs目录下，需要保证aar名称和版本号的准确
    // Baidu sdk ,通过aar方式引入， 需要把相关aar放到libs目录下，需要保证aar名称和版本号的准确
    // Ks sdk ,通过aar方式引入， 需要把相关aar放到libs目录下，需要保证aar名称和版本号的准确
    implementation(files(
        "libs/adn/GDTSDK.unionNormal.4.563.1433.aar",
        "libs/adn/Baidu_MobAds_SDK_v9.34.aar",
        "libs/adn/kssdk-ad-3.3.59.aar"
    ))

    implementation(files(
        "libs/adapter/mediation_gdt_adapter_4.563.1433.0.aar",
        "libs/adapter/mediation_baidu_adapter_9.34.0.aar",
        "libs/adapter/mediation_ks_adapter_3.3.59.0.aar"
    ))


    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")

//
//    //---------- GDT ----------
//    implementation("com.pangle.cn:mediation-gdt-adapter:4.561.1431.0")  //GDT 优量汇 adapter
//    //---------- baidu ----------
//    implementation("com.pangle.cn:mediation-baidu-adapter:9.332.0")  //Baidu adapter
//    //---------- ks ----------
//    implementation("com.pangle.cn:mediation-ks-adapter:3.3.57.0")  //KS adapter
}