package org.example

class AssignationInterpreter: InterpreterStrategy {

    override fun interpret(
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
            else -> throw Exception("Error! Not Valid Type")
        }
    }

    private fun getTokenValue(
        tree: AbstractSyntaxTree,
        variables: Map<VariableToken, String>,
    ): String? {
        val token = tree.getToken()
        return when (token.getType()) {
            Types.LITERAL -> token.getValue()
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
        variables: Map<VariableToken, String>,
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
        variables: Map<VariableToken, String>,
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
        variables: MutableMap<VariableToken, String>,
    ) {
        val processedValue = if (type == TokenType.STRING) {
            // Remove leading and trailing quotes from string value
            value.removeSurrounding("\"")
        } else {
            value // For other types, keep the value as it is
        }
        variables[VariableToken(variable, type)] = processedValue
    }


    private fun isVariableAString(
        variable: String,
        variables: Map<VariableToken, String>,
    ): Boolean {
        return variables.entries.any { it.key.value == variable && it.key.type == TokenType.STRING }
    }
}