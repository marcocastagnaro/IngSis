package org.example

//1. Get the tree
//2. Check if it is an assignation or a function
//3. If it is an assignation, get the variable, the type and the value
//4. If it is a function:
//  4.1 Check if it is an identifier or an operator
//  4.2 If it is an identifier, get the value from the map
//  4.3 If it is an operator:
//    4.3.1 Check if both or any of them are leaf or not
//    4.3.2 If both are leaf, perform the operation
//    4.3.3 If any of them is not a leaf, call the function recursively


class Interpreter2 {
    private val variables = mutableMapOf<Pair<String, String>, String>()
    private val output: Output = Output()

    fun execute(trees: List<AbstractSyntaxTree>): Output {
        for (tree in trees) {
            when (tree.getToken().getType()) {
                Types.ASSIGNATION -> interpretAssignation(tree)
                Types.FUNCTION -> interpretFunction(tree.getRight()!!)
                else -> continue
            }
        }
        return output
    }

    private fun interpretFunction(tree: AbstractSyntaxTree) {
        when (tree.getToken().getType()) {
            Types.IDENTIFIER -> solveIdentifier(tree)
            Types.OPERATOR -> solveOperator(tree)
            else -> {
                if (tree.isLeaf()) {
                    output.buildOutput(tree.getToken().getValue())
                } else {
                    if (tree.getLeft() == null || tree.getRight() == null) return
                    interpretFunction(tree.getLeft()!!)
                    interpretFunction(tree.getRight()!!)
                }
            }
        }
    }

    private fun solveIdentifier(tree: AbstractSyntaxTree) {
        val variableName = tree.getToken().getValue()
        val value = getValueForVariable(variableName)
        if (value == null) {
            throw Exception("Error! Not Valid Variable")
        } else {
            output.buildOutput(value)
        }
    }

    private fun solveOperator(tree: AbstractSyntaxTree) {
        val operator = tree.getToken().getValue()
        when (operator) {
            "+" -> sumOrConcatenation(tree)
            "*" -> executeOperation(tree, "*")
            "/" -> executeOperation(tree, "/")
            "-" -> executeOperation(tree, "-")
        }
    }

    private fun executeOperation(
        tree: AbstractSyntaxTree,
        operation: String,
    ) {
        val leftValue = getValueForVariable(tree.getLeft()!!.getToken().getValue())?.toInt()
        val rightValue = getValueForVariable(tree.getRight()!!.getToken().getValue())?.toInt()

        if (leftValue == null || rightValue == null) {
            throw Exception("Error! Not Valid Operation")
        }

        val result =
            when (operation) {
                "*" -> leftValue.times(rightValue)
                "/" -> leftValue.div(rightValue)
                "-" -> leftValue.minus(rightValue)
                else -> {
                    throw Exception("Error! Not Valid Operation")
                }
            }
        output.buildOutput(result.toString())
    }

    /*private fun sumOrConcatenation(tree: AbstractSyntaxTree) {
        val leftToken = tree.getLeft()?.getToken()
        val rightToken = tree.getRight()?.getToken()

        if (leftToken == null && rightToken == null) return

        val leftValue = leftToken?.getValue()
        val rightValue = rightToken?.getValue()

        if (leftToken == null) {
            if (existsInMap(rightValue!!, "number") || existsInMap(rightValue, "string")) {
                output.buildOutput(rightValue)
            } else {
                throw Exception("Error! Not Valid Operation")
            }
        } else if (rightToken == null) {
            if (existsInMap(leftValue!!, "number") || existsInMap(leftValue, "string")) {
                output.buildOutput(leftValue)
            } else {
                throw Exception("Error! Not Valid Operation")
            }
        } else {
            val leftExistsInMap = existsInMap(leftValue!!, "number") || existsInMap(leftValue, "string")
            val rightExistsInMap = existsInMap(rightValue!!, "number") || existsInMap(rightValue, "string")
            if (leftExistsInMap && !rightExistsInMap) {
                output.buildOutput(getValueForVariable(leftValue)!!)
                output.buildOutput(rightValue)
            } else if (!leftExistsInMap && rightExistsInMap) {
                output.buildOutput(leftValue)
                output.buildOutput(getValueForVariable(rightValue)!!)
            } else {
                val leftIsNumber = existsInMap(leftValue, "number")
                val rightIsNumber = existsInMap(rightValue, "number")

                if (leftIsNumber && rightIsNumber) {
                    val result = executeSum(tree)
                    output.buildOutput(result.toString())
                } else if (!leftIsNumber && !rightIsNumber) {
                    executeConcatenation(tree)
                } else {
                    throw Exception("Error! Not Valid Operation")
                }
            }
        }
    }*/

    private fun sumOrConcatenation(tree: AbstractSyntaxTree) {
        val leftChild = tree.getLeft()
        val rightChild = tree.getRight()

        if (leftChild == null || rightChild == null) return

        val leftValue = leftChild.getToken().getValue()
        val rightValue = rightChild.getToken().getValue()

        if (leftChild.isLeaf() && rightChild.isLeaf()) {
            if (existsInMap(leftValue, "number") && existsInMap(rightValue, "number")) {
                val result = executeSum(tree)
                output.buildOutput(result.toString())
            } else if (existsInMap(leftValue, "string") && existsInMap(rightValue, "string")) {
                output.buildOutput(getValueInMap(leftValue, "string"))
                output.buildOutput(getValueInMap(rightValue, "string"))
            } else if (existsInMap(leftValue, "string") && existsInMap(rightValue, "number")) {
                output.buildOutput(getValueInMap(leftValue, "string"))
                output.buildOutput(rightValue)
            } else if (existsInMap(leftValue) && !existsInMap(rightValue)) {
                output.buildOutput(getValueForVariable(leftValue)!!)
                output.buildOutput(rightValue)
            } else if (!existsInMap(leftValue) && existsInMap(rightValue)) {
                output.buildOutput(leftValue)
                output.buildOutput(getValueForVariable(rightValue)!!)
            } else {
                output.buildOutput(leftValue + rightValue)
            }
        } else {
            if (!leftChild.isLeaf()) {
                solveOperator(rightChild)
            } else if (!rightChild.isLeaf()) {
                solveOperator(leftChild)
            }
        }
    }

    private fun executeSum(tree: AbstractSyntaxTree): Int {
        if (tree.isLeaf()) {
            return getValueInMap(tree.getToken().getValue(), "number").toInt()
        }
        val leftValue = executeSum(tree.getLeft()!!)
        val rightValue = executeSum(tree.getRight()!!)
        return leftValue + rightValue
    }

    private fun getValueInMap(
        value: String,
        type: String,
    ): String {
        return variables[value to type]!!
    }

    private fun existsInMap(
        value: String,
        type: String,
    ): Boolean {
        return variables.containsKey(value to type)
    }

    private fun executeConcatenation(tree: AbstractSyntaxTree) {
        interpretFunction(tree.getLeft()!!)
        interpretFunction(tree.getRight()!!)
    }

    private fun existsInMap(value: String): Boolean {
        return variables.entries.any { it.key.first == value }
    }

    private fun getValueForVariable(variable: String): String? {
        return variables.entries
            .find { it.key.first == variable }?.value
    }

    private fun interpretAssignation(tree: AbstractSyntaxTree) {
        val variable = getVariable(tree)
        val type = getType(tree)
        val rightValue = getRightValue(tree)

        if (variable != null && rightValue != null && (type == "number" || type == "string")) {
            variables[variable to type] = rightValue
        }
    }

    private fun addVariableToMap(
        variable: String,
        value: String,
        type: String,
    ) {
        variables[variable to type] = value
    }

    private fun getVariable(tree: AbstractSyntaxTree): String? {
        return tree.getLeft()?.getRight()?.getLeft()?.getToken()?.getValue()
    }

    private fun getType(tree: AbstractSyntaxTree): String? {
        return tree.getLeft()?.getRight()?.getRight()?.getToken()?.getValue()
    }

    private fun getRightValue(tree: AbstractSyntaxTree): String? {
        return tree.getRight()?.getToken()?.getValue()
    }
}
