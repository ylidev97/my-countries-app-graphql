@file:Suppress("UnstableApiUsage")

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.apollo)
    alias(libs.plugins.kotzilla)
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.lidev.mycountriesapp"
    compileSdk {
        version =
            release(36) {
                minorApiLevel = 1
            }
    }

    defaultConfig {
        applicationId = "com.lidev.mycountriesapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            applicationIdSuffix = ".debug"

            // Append "-debug" to the versionName (e.g., "1.0-debug")
            versionNameSuffix = "-debug"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    testOptions {
        suites {
            create("journeysTest") {
                assets {
                }
                targets {
                    create("default") {
                    }
                }
                @Suppress("UnstableApiUsage")
                useJunitEngine {
                    inputs += listOf(com.android.build.api.dsl.AgpTestSuiteInputParameters.TESTED_APKS)
                    includeEngines += listOf("journeys-test-engine")
                    enginesDependencies(libs.junit.platform.launcher)
                    enginesDependencies(libs.junit.platform.engine)
                    enginesDependencies(libs.journeys.junit.engine)
                }
                targetVariants += listOf("debug")
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Network graphql
    implementation(libs.apollo.runtime)
    implementation(libs.apollo.cache)

    implementation(libs.coroutines)
    implementation(libs.io.coil.kt)
    implementation(libs.io.coil.kt.network)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.com.airbnb.lottie.compose)
    implementation(libs.androidx.compose.material.icons.extended)

    // Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.navigation)
    implementation(libs.koin.test.implementation)
    implementation(libs.kotzilla.sdk)
}

apollo {
    service("services") {
        srcDir("src/main/graphql/countriestrevorblades")
        packageName.set("com.lidev.mycountriesapp.countriestrevorblades")
        introspection {
            endpointUrl.set("https://countries.trevorblades.com/graphql")
            schemaFile.set(file("src/main/graphql/countriestrevorblades/schema.graphqls"))
        }
    }
}
kotzilla { autoAddDependencies = false }

androidComponents {
    onVariants { variant ->
        variant.outputs.forEach { output ->
            val versionName = output.versionName.get()
            val outputImpl = output as? com.android.build.api.variant.impl.VariantOutputImpl
            outputImpl?.outputFileName?.set(
                "com-lidev-mycountriesapp-v$versionName-${variant.buildType}.apk",
            )
        }
    }
}

detekt {
    toolVersion = libs.versions.detekt.get()
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom(files("${project.rootDir}/config/detekt/detekt.yml"))
    baseline =
        file("$projectDir/config/baseline.xml") // a way of suppressing issues before introducing detekt
    ignoreFailures = true
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "1.8"

    reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(false)
        sarif.required.set(true)
        // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with GitHub Code Scanning

//        html.outputLocation.set(file("$buildDir/reports/detekt/detekt.html"))
//        xml.outputLocation.set(file("$buildDir/reports/detekt/detekt.xml"))
        // New layout.buildDirectory for get the file
        html.outputLocation.set(layout.buildDirectory.file("reports/detekt/detekt.html"))
        xml.outputLocation.set(layout.buildDirectory.file("reports/detekt/detekt.xml"))
    }
}

// Kotlin DSL
tasks.withType<Detekt>().configureEach {
    jvmTarget = "1.8"
}
tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "1.8"
}
