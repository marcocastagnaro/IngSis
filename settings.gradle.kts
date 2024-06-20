plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "ingsis"
include("ast", "lexer", "parser", "token")
include("interpreter")
include("formatter")
include("execute")
include("staticCodeeAnalyzer")
include("cli")
include("untitled")
