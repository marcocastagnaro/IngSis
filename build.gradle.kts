
plugins {
    kotlin("jvm") version "1.9.21"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
    id("org.jetbrains.kotlinx.kover") version "0.7.6"
}

allprojects {
    apply(plugin = "org.jetbrains.kotlinx.kover")
}

koverReport {
    verify {
        rule {
            isEnabled = true
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    kover(project(":lexer"))
    kover(project(":parser"))
    kover(project(":interpreter"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}