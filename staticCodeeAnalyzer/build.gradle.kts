plugins {
    id("org.example.basic-plugin")
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation(project(":ast"))
    implementation(project(":token"))
    testImplementation(project(":lexer"))
    testImplementation(project(":parser"))
    implementation(project(":formatter"))
    testImplementation(project(":formatter"))
    testImplementation(project(":token"))
    testImplementation(project(":ast"))
}
