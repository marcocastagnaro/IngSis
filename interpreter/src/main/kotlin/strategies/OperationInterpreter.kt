package org.example.strategies

class OperationInterpreter {
    fun performOperation(
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
            val isAInt = a.toDoubleOrNull() != null
            val isBInt = b.toDoubleOrNull() != null
            if (isAInt && isBInt) {
                trimDecimalsIfPossible(a.toDouble() + b.toDouble())
            } else {
                a + b
            }
        }
    }

    private fun subtract(
        a: String,
        b: String,
    ): Number {
        val isAInt = a.toDoubleOrNull() != null
        val isBInt = b.toDoubleOrNull() != null
        return when {
            isAInt && isBInt -> trimDecimalsIfPossible(a.toDouble() - b.toDouble())
            else -> throw IllegalArgumentException("Unsupported operand types for subtraction: $a and $b")
        }
    }

    private fun multiply(
        a: String,
        b: String,
    ): Number {
        val isAInt = a.toDoubleOrNull() != null
        val isBInt = b.toDoubleOrNull() != null

        return when {
            isAInt && isBInt -> trimDecimalsIfPossible(a.toDouble() * b.toDouble())
            else -> throw IllegalArgumentException("Unsupported operand types for multiplication: $a and $b")
        }
    }

    private fun divide(
        a: String,
        b: String,
    ): Number {
        val isAInt = a.toDoubleOrNull() != null
        val isBInt = b.toDoubleOrNull() != null

        return when {
            isAInt && isBInt -> trimDecimalsIfPossible(a.toDouble() / b.toDouble())
            else -> throw IllegalArgumentException("Unsupported operand types for division: $a and $b")
        }
    }

    private fun isVariableAString(
        variable: String,
        variables: MutableMap<VariableToken, String?>,
    ): Boolean {
        return variables.entries.any { it.key.value == variable && it.key.type == TokenType.STRING }
    }

    private fun trimDecimalsIfPossible(value: Double): Number {
        return if (value % 1 == 0.0) {
            value.toInt()
        } else {
            value
        }
    }
}
