plugins {
    id("org.example.basic-plugin")
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation(project(":AST"))
    implementation(project(":Token"))
    testImplementation(project(":parser"))
    testImplementation(project(":lexer"))
    testImplementation(project(":interpreter"))
    implementation("com.github.ajalt.clikt:clikt:4.2.0")

}
