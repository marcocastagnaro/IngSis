package org.example.strategies

import org.example.AbstractSyntaxTree
import org.example.InterpreterStrategy
import org.example.Types
import org.example.inputReader.InputReaderType

class AssignationInterpreter(private val inputReader: InputReaderType) : InterpreterStrategy {
    override fun interpret(
        tree: AbstractSyntaxTree,
        variables: HashMap<VariableToken, String?>,
    ): Map<VariableToken, String?> {
        val tempMap = variables.toMutableMap()
        if (tree.getLeft()?.getToken()?.getType() == Types.IDENTIFIER) {
            val variable = tree.getLeft()?.getToken()?.getValue()
            val value = getTokenValue(tree.getRight()!!, variables)
            val actualValue = tempMap.entries.firstOrNull { it.key.value == variable }
            if (actualValue != null) {
                tempMap[VariableToken(variable!!, actualValue.key.type)] = value
            } else {
                throw IllegalArgumentException("Variable $variable no declarada")
            }
        } else {
            addValuesToMap(tree, tempMap)
        }
        return tempMap
    }

    private fun addValuesToMap(
        tree: AbstractSyntaxTree,
        variables: MutableMap<VariableToken, String?>,
    ) {
        val variable = getVariable(tree)
        val type = getType(tree)
        val value = getTokenValue(tree.getRight()!!, variables)
        addVariable(variable!!, type, value!!, variables)
    }

    private fun getVariable(tree: AbstractSyntaxTree): String? {
        return tree.getLeft()?.getRight()?.getLeft()?.getToken()?.getValue()
    }

    private fun getType(tree: AbstractSyntaxTree): TokenType {
        val value = tree.getLeft()?.getRight()?.getRight()?.getToken()?.getValue()
        return when (value) {
            "number" -> TokenType.NUMBER
            "string" -> TokenType.STRING
            "boolean" -> TokenType.BOOLEAN
            else -> throw Exception("Error! Not Valid Type")
        }
    }

    private fun getTokenValue(
        tree: AbstractSyntaxTree,
        variables: MutableMap<VariableToken, String?>,
    ): String? {
        val token = tree.getToken()
        return when (token.getType()) {
            Types.READENV -> ReadEnvInterpreter().readEnvVariables(tree.getRight()!!)
            Types.LITERAL, Types.BOOLEAN -> token.getValue()
            Types.FUNCTION -> ReadInputInterpreter(inputReader).getInput(tree, Types.FUNCTION)
            Types.OPERATOR -> {
                val leftValue = getTokenValue(tree.getLeft()!!, variables)
                val rightValue = getTokenValue(tree.getRight()!!, variables)
                performOperation(token.getValue(), leftValue!!, rightValue!!, variables).toString()
            }

            else -> null
        }
    }

    private fun performOperation(
        operator: String,
        left: String,
        right: String,
        variables: MutableMap<VariableToken, String?>,
    ): Any {
        return when (operator) {
            "+" -> add(left, right, variables)
            "-" -> subtract(left, right)
            "*" -> multiply(left, right)
            "/" -> divide(left, right)
            else -> throw IllegalArgumentException("Unsupported operator: $operator")
        }
    }

    private fun add(
        a: String,
        b: String,
        variables: MutableMap<VariableToken, String?>,
    ): Any {
        val isConcatenation = isVariableAString(a, variables) || isVariableAString(b, variables)
        return if (isConcatenation) {
            "$a$b"
        } else {
            val isAInt = a.toIntOrNull() != null
            val isBInt = b.toIntOrNull() != null
            if (isAInt && isBInt) {
                a.toInt() + b.toInt()
            } else {
                a + b
            }
        }
    }

    private fun subtract(
        a: String,
        b: String,
    ): Any {
        val isAInt = a.toIntOrNull() != null
        val isBInt = b.toIntOrNull() != null
        return when {
            isAInt && isBInt -> a.toInt() - b.toInt()
            else -> throw IllegalArgumentException("Unsupported operand types for subtraction: $a and $b")
        }
    }

    private fun multiply(
        a: String,
        b: String,
    ): Int {
        val isAInt = a.toIntOrNull() != null
        val isBInt = b.toIntOrNull() != null

        return when {
            isAInt && isBInt -> a.toInt() * b.toInt()
            else -> throw IllegalArgumentException("Unsupported operand types for multiplication: $a and $b")
        }
    }

    private fun divide(
        a: String,
        b: String,
    ): Int {
        val isAInt = a.toIntOrNull() != null
        val isBInt = b.toIntOrNull() != null

        return when {
            isAInt && isBInt -> a.toInt() / b.toInt()
            else -> throw IllegalArgumentException("Unsupported operand types for division: $a and $b")
        }
    }

    private fun addVariable(
        variable: String,
        type: TokenType,
        value: String,
        variables: MutableMap<VariableToken, String?>,
    ) {
        val processedValue =
            if (type == TokenType.STRING) {
                value.removeSurrounding("\"")
            } else {
                value
            }
        variables[VariableToken(variable, type)] = processedValue
    }

    private fun isVariableAString(
        variable: String,
        variables: MutableMap<VariableToken, String?>,
    ): Boolean {
        return variables.entries.any { it.key.value == variable && it.key.type == TokenType.STRING }
    }
}
