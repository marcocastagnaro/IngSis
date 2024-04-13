plugins {
    id("org.example.basic-plugin")
    id("publish-plugin")
}

version = "1.0.0-SNAPSHOT"

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation(project(":ast"))
    implementation(project(":token"))
    testImplementation(project(":lexer"))
    testImplementation(project(":parser"))
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("org.mockito:mockito-junit-jupiter:4.0.0")
}
