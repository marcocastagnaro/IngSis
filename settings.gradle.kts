plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "Ingsis"
include("AST", "lexer", "parser", "Token")
include("IntegrationTesting")
include("interpreter")
include("formatter")
include("cli")
