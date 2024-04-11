plugins {
    id("org.example.basic-plugin")
    id("application")
}
repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation(project(":AST"))
    implementation(project(":Token"))
    implementation(project(":parser"))
    implementation(project(":lexer"))
    implementation(project(":interpreter"))
    implementation(project(":formatter"))
    implementation(project(":staticCodeeAnalyzer"))
    implementation("com.github.ajalt.clikt:clikt:4.3.0")
}

application {
    mainClass.set("cli.PrintScriptKt")
}
