plugins {
    id("org.example.basic-plugin")
}


dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation(project(":AST"))
    implementation(project(":Token"))
    testImplementation(project(":lexer"))
    testImplementation(project(":parser"))
    implementation(project(":formatter"))
    testImplementation(project(":formatter"))
    testImplementation(project(":Token"))
    testImplementation(project(":AST"))
}
