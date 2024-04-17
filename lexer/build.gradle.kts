plugins {
    id("org.example.basic-plugin")
    id("publish-plugin")
}

version = "1.1.4-SNAPSHOT"

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation(project(":token"))
    implementation(project(":ast"))
}
