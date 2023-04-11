import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.nevrozka"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.material3)
                implementation("io.github.alexgladkov:odyssey-core:1.3.1")
                implementation("io.github.alexgladkov:odyssey-compose:1.3.1")
                implementation("com.adeo:kviewmodel:0.14") // Core functions
                implementation("com.adeo:kviewmodel-compose:0.14") // Compose extensions
                implementation("com.adeo:kviewmodel-odyssey:0.14") // Odyssey extensions
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "SvetlanaOGE"
            packageVersion = "1.0.0"
            macOS{
                iconFile.set(project.file("src/jvmMain/resources/icon.icns"))
            }
            windows {
                iconFile.set(project.file("src/jvmMain/resources/icon.ico"))
            }
        }
    }
}
