plugins {
    id("org.example.basic-plugin")
    id("publish-plugin")
}

version = "1.1.5-SNAPSHOT"

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation(project(":ast"))
    implementation(project(":token"))
    implementation(project(":lexer"))
    implementation(project(":parser"))
    testImplementation(kotlin("test"))
}
