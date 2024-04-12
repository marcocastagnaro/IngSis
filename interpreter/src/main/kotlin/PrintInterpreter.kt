package org.example

class PrintInterpreter : InterpreterStrategy {
    override fun interpret(
        tree: AbstractSyntaxTree,
        variables: HashMap<VariableToken, String?>,
    ): Map<VariableToken, String> {
        val result = evaluateNode(tree.getRight()!!, variables)
        return hashMapOf(VariableToken("printResult", TokenType.PRINT) to result.toString())
    }

    private fun evaluateNode(
        tree: AbstractSyntaxTree,
        variables: HashMap<VariableToken, String?>,
    ): Any {
        return when (tree.getToken().getType()) {
            Types.OPERATOR -> {
                val leftValue = evaluateNode(tree.getLeft()!!, variables)
                val rightValue = evaluateNode(tree.getRight()!!, variables)
                performOperation(tree.getToken().getValue(), leftValue.toString(), rightValue.toString(), variables)
            }
            Types.IDENTIFIER -> getValueForVariable(variables, tree.getToken().getValue()) ?: 0
            Types.LITERAL -> tree.getToken().getValue()
            else -> throw IllegalArgumentException("Unsupported token type: ${tree.getToken().getType()}")
        }
    }

    private fun performOperation(
        operator: String,
        left: String,
        right: String,
        variables: HashMap<VariableToken, String?>,
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
        variables: HashMap<VariableToken, String?>,
    ): Any {
        val isConcatenation = isVariableAString(a, variables) && isVariableAString(b, variables)
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

    private fun getValueForVariable(
        map: HashMap<VariableToken, String?>,
        variable: String,
    ): String? {
        return map.entries.find { it.key.value == variable }?.value
    }

    private fun isVariableAString(
        variable: String,
        variables: HashMap<VariableToken, String?>,
    ): Boolean {
        return variables.entries.any { it.key.value == variable && it.key.type == TokenType.STRING }
    }
}
