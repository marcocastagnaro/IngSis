plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "Ingsis"
include("untitled")
include("Interpreter", "AST", "lexer", "parser", "Token")

