package org.example

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class ReadEnvInterpreter {
    fun readEnvVariables(tree: AbstractSyntaxTree): String {
        val token = tree.getToken()
        if (token.getType() == Types.IDENTIFIER) {
            val envFilePath = Paths.get("../.env")

            if (!Files.exists(envFilePath)) {
                throw IllegalArgumentException("El archivo de variables de entorno no se encontró en $envFilePath")
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
                    ?: throw IllegalArgumentException("Variable $variableName no encontrada en el archivo .env")
            } else {
                throw IllegalArgumentException("Variable $variableName no encontrada en el archivo .env")
            }
        }
        throw IllegalArgumentException("Nombre de variable inválido: ${token.getValue()}")
    }

    fun printEnvVariable(tree: AbstractSyntaxTree): String {
        val envVariables = readEnvVariables(tree)
        return envVariables
    }
}
