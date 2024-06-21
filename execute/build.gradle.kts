plugins {
    id("org.example.basic-plugin")
    id("publish-plugin")
}

version = "1.1.3-SNAPSHOT"

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation(project(":ast"))
    implementation(project(":token"))
    implementation(project(":parser"))
    implementation(project(":lexer"))
    implementation(project(":interpreter"))
    implementation(project(":formatter"))
    implementation(project(":staticcodeeanalyzer"))
}
