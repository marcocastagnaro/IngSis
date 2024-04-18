package org.example.strategies

import org.example.AbstractSyntaxTree
import org.example.InterpreterStrategy
import org.example.Output
import org.example.Types
import org.example.inputReader.InputReaderType
import org.example.strategies.strategyHelpers.OperationInterpreter
import org.example.strategies.strategyHelpers.ReadEnvInterpreter
import org.example.strategies.strategyHelpers.ReadInputInterpreter
import kotlin.collections.HashMap

class PrintInterpreter(
    private val inputReader: InputReaderType,
    private val output: Output,
) : InterpreterStrategy {
    override fun interpret(
        tree: AbstractSyntaxTree,
        variables: HashMap<VariableToken, String?>,
        inmutableList: MutableList<String>,
    ): Map<VariableToken, String> {
        val result = evaluateNode(tree.getRight()!!, variables)
        return hashMapOf(VariableToken(getId(variables), TokenType.PRINT) to removeStringQuotes(result.toString()))
    }

    private fun getId(variables: HashMap<VariableToken, String?>): String {
        val maxValue =
            variables.entries
                .filter { it.key.type == TokenType.PRINT } // Filter entries with TokenType.PRINT
                .mapNotNull { it.key.value.toIntOrNull() } // Convert values to Int, ignoring null values
                .maxOrNull() // Find the maximum value

        return (maxValue?.plus(1) ?: 0).toString() // Return the maximum value + 1, or 0 if no value is found
    }

    private fun removeStringQuotes(value: String): String {
        if (!value.startsWith("\"") || !value.endsWith("\"")) return value
        return value.removePrefix("\"").removeSuffix("\"")
    }

    private fun evaluateNode(
        tree: AbstractSyntaxTree,
        variables: HashMap<VariableToken, String?>,
    ): Any {
        return when (tree.getToken().getType()) {
            Types.OPERATOR -> {
                val leftValue = evaluateNode(tree.getLeft()!!, variables)
                val rightValue = evaluateNode(tree.getRight()!!, variables)
                OperationInterpreter().performOperation(tree.getToken().getValue(), leftValue.toString(), rightValue.toString(), variables)
            }
            Types.IDENTIFIER -> getValueForVariable(variables, tree.getToken().getValue()) ?: 0
            Types.LITERAL -> tree.getToken().getValue()
            Types.FUNCTION -> defineFunction(tree)
            else -> throw IllegalArgumentException("Unsupported token type: ${tree.getToken().getType()}")
        }
    }

    private fun defineFunction(tree: AbstractSyntaxTree): String {
        if (tree.getToken().getValue() == "readEnv") {
            return ReadEnvInterpreter().readEnvVariables(tree.getRight()!!)
        } else if (tree.getToken().getValue() == "readInput") {
            return ReadInputInterpreter(inputReader).getInput(tree, Types.FUNCTION, output)
        } else {
            throw IllegalArgumentException("Unsupported function: ${tree.getToken().getValue()}")
        }
    }

    private fun getValueForVariable(
        map: HashMap<VariableToken, String?>,
        variable: String,
    ): String? {
        return map.entries.find { it.key.value == variable }?.value
    }
}
