plugins {
    id("org.example.basic-plugin")
    id("application")
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation(project(":ast"))
    implementation(project(":token"))
    implementation(project(":parser"))
    implementation(project(":lexer"))
    implementation(project(":interpreter"))
    implementation(project(":formatter"))
    implementation(project(":staticCodeeAnalyzer"))
    implementation("com.github.ajalt.clikt:clikt:4.2.0")
}
application {
    mainClass.set("cli.CLIKt")
}
