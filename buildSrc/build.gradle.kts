plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.jlleitschuh.gradle:ktlint-gradle:12.1.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
}
