plugins {
    id("org.example.basic-plugin")
    id("application")
    id("publish-plugin")
}

repositories {
    mavenCentral()
}

version = "1.1.0-SNAPSHOT"

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation(project(":ast"))
    implementation(project(":token"))
    implementation(project(":parser"))
    implementation(project(":lexer"))
    implementation(project(":interpreter"))
    implementation(project(":formatter"))
    implementation(project(":staticCodeeAnalyzer"))
}

application {
    mainClass.set("executer.PrintScriptKt")
}
