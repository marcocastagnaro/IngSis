plugins {
    id("org.example.basic-plugin")
    id("publish-plugin")
}

version = "1.1.1-SNAPSHOT"

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation(project(":ast"))
    implementation(project(":token"))
    testImplementation(project(":lexer"))
    testImplementation(project(":parser"))
}
