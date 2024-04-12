import org.jetbrains.kotlin.fir.declarations.builder.buildScript
import org.jetbrains.kotlin.name.Name

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed



//mediationAutoAdapter {
//    //自动适配adn 所有adn adapter，默认值为true，如果为false脚本功能全部关闭
//    autoUpdate = true
//    //如果不想全部自动适配，可选择此项，默认值为空[]，可以不填写
//    //如果autoUpdate设置成false，此项不生效，不会自动适配adn adapter
//    //如果autoUpdate设置成true，autoUpdateAdn配置了adn，则生效配置的adn。
//    //autoUpdateAdn没有配置adn，自动适配所有的adn
//    autoUpdateAdn = ["gdt","baidu","ks"]
//}


plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    kotlin("kapt")
//
//    buildScript {
//        name = Name.identifier("mediationAutoAdapter")
//        resultPropertyName = Name.identifier("autoUpdate")
//
//        build()
//    }
}

android {
    namespace = "com.ziguhonglan.plugins.center"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.daodao.note"
//        applicationId = "com.ziguhonglan.plugins.center"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
        dataBinding = true
        viewBinding = true
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
//    implementation fileTree(include: ['*.jar'], dir: 'libs')
//    implementation 'com.pangle.cn:mediation-sdk:5.9.0.8' //穿山甲融合SDK
//
//    //---------- GDT ----------
//    implementation(name: "GDTSDK.unionNormal.4.561.1431", ext: 'aar')  //GDT 优量汇 sdk ,通过aar方式引入， 需要把相关aar放到libs目录下，需要保证aar名称和版本号的准确
//    //---------- baidu ----------
//    implementation(name: "Baidu_MobAds_SDK_v9.332", ext: 'aar')  //Baidu sdk ,通过aar方式引入， 需要把相关aar放到libs目录下，需要保证aar名称和版本号的准确
//    //---------- ks ----------
//    implementation(name: "kssdk-ad-3.3.57", ext: 'aar') //Ks sdk ,通过aar方式引入， 需要把相关aar放到libs目录下，需要保证aar名称和版本号的准确

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(project(":gromore"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

