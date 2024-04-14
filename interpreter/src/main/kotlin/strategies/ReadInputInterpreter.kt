package org.example.strategies

import org.example.AbstractSyntaxTree
import org.example.ParseTreeToTokens
import org.example.Types
import org.example.inputReader.InputReaderType
import org.example.utils.ClearCommas

class ReadInputInterpreter(private val inputReader: InputReaderType) {
    fun getInput(
        ast: AbstractSyntaxTree,
        statementType: Types,
    ): String {
        val msg = getMessage(ast)
        if (statementType == Types.FUNCTION) {
            return formatInputmessage(msg) + readInput()
        } else if (statementType == Types.ASSIGNATION) {
            return formatInputmessage(msg) + checkInput(ast)
        } else {
            throw Exception("Error! Not Valid Type")
        }
    }

    private fun formatInputmessage(msg: String): String {
        if (msg.length > 1) {
            return ClearCommas().clearCommas(msg + "\n")
        }
        return ""
    }

    private fun getMessage(ast: AbstractSyntaxTree): String {
        val tokens = ParseTreeToTokens().parseToTokens(ast)
        val values = tokens.filter { it.getType() == Types.LITERAL }
        return values.map { it.getValue() }.joinToString(" ")
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
        return ClearCommas().clearCommas(inputReader.input())
    }
}
