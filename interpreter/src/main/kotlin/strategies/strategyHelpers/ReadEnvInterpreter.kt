package org.example.strategies.strategyHelpers

import org.example.AbstractSyntaxTree
import org.example.Types
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class ReadEnvInterpreter {
    fun readEnvVariables(tree: AbstractSyntaxTree): String {
        val token = tree.getToken()
        if (token.getType() == Types.IDENTIFIER) {
            val envFilePath = Paths.get("../.env")

            if (!Files.exists(envFilePath)) {
                throw IllegalArgumentException("File not found on file path: $envFilePath")
            }

            val envVariables = mutableMapOf<String, String>()

            File(envFilePath.toString()).forEachLine { line ->
                if (line.contains("=")) {
                    val (key, value) = line.split("=", limit = 2)
                    envVariables[key.trim()] = value.trim()
                }
            }

            val variableName = token.getValue()

            if (envVariables.containsKey(variableName)) {
                return envVariables[variableName]
                    ?: throw IllegalArgumentException("Variable $variableName not found on file .env")
            } else {
                throw IllegalArgumentException("Variable $variableName not found on file .env")
            }
        }
        throw IllegalArgumentException("Invalid name variable: ${token.getValue()}")
    }

    fun printEnvVariable(tree: AbstractSyntaxTree): String {
        val envVariables = readEnvVariables(tree)
        return envVariables
    }
}
