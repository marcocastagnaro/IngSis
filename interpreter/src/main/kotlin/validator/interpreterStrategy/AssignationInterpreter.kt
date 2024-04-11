package org.example.validator.interpreterStrategy

import org.example.AbstractSyntaxTree
import org.example.TokenType
import org.example.Types
import org.example.VariableToken

class AssignationInterpreter : InterpreterStrategy {
    public override fun interpret(
        tree: AbstractSyntaxTree,
        variables: Map<VariableToken, String>,
    ): Map<VariableToken, String> {
        val tempMap = variables.toMutableMap()
        addValuesToMap(tree, tempMap)
        return tempMap
    }

    private fun addValuesToMap(
        tree: AbstractSyntaxTree,
        variables: MutableMap<VariableToken, String>,
    ) {
        val variable = getVariable(tree)
        val type = getType(tree)
        val value = getTokenValue(tree.getRight()!!)
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
            else -> throw Exception("Error! Not Valid Type")
        }
    }

    private fun getTokenValue(tree: AbstractSyntaxTree): String? {
        val token = tree.getToken()
        return when (token.getType()) {
            Types.LITERAL -> token.getValue()
            Types.OPERATOR -> {
                val leftValue = getTokenValue(tree.getLeft()!!)
                val rightValue = getTokenValue(tree.getRight()!!)
                performOperation(token.getValue(), leftValue!!, rightValue!!).toString()
            }
            else -> null
        }
    }

    private fun performOperation(
        operator: String,
        left: Any,
        right: Any,
    ): Any {
        return when (operator) {
            "+" -> add(left, right)
            "-" -> subtract(left, right)
            "*" -> multiply(left, right)
            "/" -> divide(left, right)
            else -> throw IllegalArgumentException("Unsupported operator: $operator")
        }
    }

    private fun add(
        a: Any,
        b: Any,
    ): Any {
        return if (a is String || b is String) {
            "$a$b"
        } else {
            when (a) {
                is Int -> a + (b as Int)
                is Double -> a + (b as Double)
                else -> throw IllegalArgumentException("Unsupported operand types for addition: $a and $b")
            }
        }
    }

    private fun subtract(
        a: Any,
        b: Any,
    ): Any {
        return when (a) {
            is Int -> a - (b as Int)
            is Double -> a - (b as Double)
            else -> throw IllegalArgumentException("Unsupported operand types for subtraction: $a and $b")
        }
    }

    private fun multiply(
        a: Any,
        b: Any,
    ): Any {
        return when (a) {
            is Int -> a * (b as Int)
            is Double -> a * (b as Double)
            else -> throw IllegalArgumentException("Unsupported operand types for multiplication: $a and $b")
        }
    }

    private fun divide(
        a: Any,
        b: Any,
    ): Any {
        return when (a) {
            is Int -> a / (b as Int)
            is Double -> a / (b as Double)
            else -> throw IllegalArgumentException("Unsupported operand types for division: $a and $b")
        }
    }

    private fun addVariable(
        variable: String,
        type: TokenType,
        value: String,
        variables: MutableMap<VariableToken, String>,
    ) {
        variables[VariableToken(variable, type)] = value
    }
}
