package org.example.strategies

import org.example.AbstractSyntaxTree
import org.example.ParseTreeToTokens
import org.example.Types
import org.example.inputReader.InputReaderType

class ReadInputInterpreter(val inputReader: InputReaderType) {
    fun getInput(
        ast: AbstractSyntaxTree,
        statementType: Types,
    ): String {
        print(getMessage(ast))
        if (statementType == Types.FUNCTION) {
            return readInput()
        } else if (statementType == Types.ASSIGNATION) {
            return checkInput(ast)
        } else {
            throw Exception("Error! Not Valid Type")
        }
    }

    private fun getMessage(ast: AbstractSyntaxTree): String {
        val tokens = ParseTreeToTokens().parseToTokens(ast)
        val values = tokens.filter { it.getType() == Types.LITERAL }
        return values.map { it.getValue() }.toString()
    }

    private fun checkInput(ast: AbstractSyntaxTree): String {
        val input = readInput()
        val tokens = ParseTreeToTokens().parseToTokens(ast)
        val variableType = tokens.filter { it.getType() == Types.DATA_TYPE }.first().getValue()
        if (variableType == "number") {
            if (!input.matches(Regex("[0-9]+"))) {
                throw Exception("Error! Not Valid Type")
            }
        } else if (variableType == "string") {
            if (!input.matches(Regex("(\\d+|\"[^\"]*\"|'[^']*')"))) {
                throw Exception("Error! Not Valid Type")
            }
        }
        return input
    }

    private fun readInput(): String {
        return inputReader.input()
    }
}
