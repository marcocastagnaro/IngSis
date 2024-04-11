plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "ingSis"
include("AST", "lexer", "parser", "Token")
include("interpreter")
include("formatter")
include("cli")
include("staticCodeeAnalyzer")
