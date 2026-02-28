val fileFilter =
    mutableSetOf(
        "**/R.class",
        "**/R\$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*",
        "**/*\$ViewInjector*.*",
        "**/*\$ViewBinder*.*",
        "**/Lambda\$*.class",
        "**/Lambda.class",
        "**/*Lambda.class",
        "**/*Lambda*.class",
        "**/*_MembersInjector.class",
        "**/Dagger*Component*.*",
        "**/*Module_*Factory.class",
        "**/di/module/*",
        "**/*_Factory*.*",
        "**/*_Provides*Factory*.*",
        "**/*Extensions*.*",
        "**/*\$Result.*",
        "**/*\$Result\$*.*",
    )

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")
    dependsOn("assembleDebug")

    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(true)
    }

    val kotlinClasses = layout.buildDirectory.dir("tmp/kotlin-classes/debug")
    val javaClasses = layout.buildDirectory.dir("intermediates/javac/debug/classes")

    classDirectories.setFrom(
        files(
            fileTree(kotlinClasses) { exclude(fileFilter) },
            fileTree(javaClasses) { exclude(fileFilter) },
        ),
    )

    val mainSrc = files("src/main/java", "src/main/kotlin")

    sourceDirectories.setFrom(mainSrc)
    executionData.setFrom(
        fileTree(layout.buildDirectory) {
            include(
                "jacoco/testDebugUnitTest.exec",
                "outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec",
            )
        },
    )

    doLast {
        val reportFile =
            reports
                .html
                .outputLocation
                .get()
                .asFile
                .resolve("index.html")
        if (reportFile.exists()) {
            println("Jacoco report generated at: file://${reportFile.absolutePath}")
            val os = System.getProperty("os.name").lowercase()
            val command =
                when {
                    os.contains("mac") -> arrayOf("open", reportFile.absolutePath)
                    os.contains("win") -> arrayOf("cmd", "/c", "start", reportFile.absolutePath)
                    else -> arrayOf("xdg-open", reportFile.absolutePath)
                }
            try {
                Runtime.getRuntime().exec(command)
            } catch (e: Exception) {
                println("Could not open report automatically: ${e.message}")
            }
        } else {
            println("Jacoco report file not found: ${reportFile.absolutePath}")
        }
    }
}
