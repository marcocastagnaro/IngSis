plugins {
    id("org.example.basic-plugin")
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation(project(":AST"))
    implementation(project(":Token"))
    implementation(project(":parser"))
    implementation(project(":lexer"))
    implementation(project(":interpreter"))
    implementation("com.github.ajalt.clikt:clikt:4.2.0")

}
