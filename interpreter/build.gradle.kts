plugins {
    id("org.example.basic-plugin")
    id("publish-plugin")
}

version = "1.0.0-SNAPSHOT"

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation(project(":AST"))
    implementation(project(":Token"))
}
