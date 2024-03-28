plugins {
    id("org.example.basic-plugin")
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation(project(":parser"))
    testImplementation(project(":lexer"))
    testImplementation(project(":interpreter"))
}
