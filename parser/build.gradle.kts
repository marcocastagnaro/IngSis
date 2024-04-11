plugins {
    id("org.example.basic-plugin")
    id("publish-plugin")
}

version = "1.0.0-SNAPSHOT"

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation(project(":token"))
    implementation(project(":ast"))
    implementation(project(":lexer"))
}
