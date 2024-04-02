package org.example

class Interpreter(private var trees: List<AbstractSyntaxTree>) {
    private val mapValuesAndVariables = mutableMapOf<Pair<String, String>, String>()

// el Pair de string,string es para guardar el nombre de la variable y su tipo, luego el value es el valor de la variable
    fun execute(): Output {
        val output: Output = Output()

        for (tree in trees) {
            when (tree.getToken().getValue()) {
                "=" -> { // si es una asignacion. ASUMIENDO QUE NO HAY ERRORES
                    // TODO (Falta el coso eso que busca errores y formatea)
                    getValues(tree, output)
                }

                "println" -> { // si es un println
                    getPrintOutput(tree.getRight()!!, output)
                }
            }
        }
        return output
    }

    private fun getPrintOutput(
        tree: AbstractSyntaxTree,
        output: Output,
    ) {
        if (tree.getToken().getType() == Types.IDENTIFIER) {
            val variableName = tree.getToken().getValue()
            val value = testingVariables(variableName)
            if (value == null) {
                throw Exception("Error! Not Valid Variable")
//                println("Error! Not Valid Variable")
            } else {
                output.buildOutput(value)
            }
        } else if (tree.isLeaf()) {
            output.buildOutput(tree.getToken().getValue())
        }

        if (tree.getToken().getType() == Types.OPERATOR) {
            val operator = tree.getToken().getValue()
            if (operator == "+") {
                executeConcatenation(tree, output)
            }
            if (operator == "*") {
                val result =
                    tree.getLeft()!!.getToken().getValue().toInt()
                        .times(tree.getRight()!!.getToken().getValue().toInt())
                output.buildOutput(result.toString())
            }
            if (operator == "/") {
                val result =
                    tree.getLeft()!!.getToken().getValue().toInt()
                        .div(tree.getRight()!!.getToken().getValue().toInt())
                output.buildOutput(result.toString())
            }
            if (operator == "-") {
                val result =
                    tree.getLeft()!!.getToken().getValue().toInt()
                        .minus(tree.getRight()!!.getToken().getValue().toInt())
                output.buildOutput(result.toString())
            }
        } else {
            if (tree.getLeft() == null || tree.getRight() == null) return
            getPrintOutput(tree.getLeft()!!, output)
            getPrintOutput(tree.getRight()!!, output)
        }
    }

    private fun executeConcatenation(
        tree: AbstractSyntaxTree,
        output: Output,
    ) {
        getPrintOutput(tree.getLeft()!!, output)
        getPrintOutput(tree.getRight()!!, output)
    }

    private fun getValues(
        tree: AbstractSyntaxTree,
        output: Output,
    ) {
        val variable = tree.getLeft()?.getLeft()?.getToken()?.getValue()
        val type = tree.getLeft()?.getRight()?.getToken()?.getValue()
        val rightValue = tree.getRight()?.getToken()?.getValue()

        // TODO : si es una suma o conjunto de numeros esto no es valido
        if (variable != null && rightValue != null && (type == "number" || type == "string")) {
            mapValuesAndVariables[variable to type] = rightValue
        }
    }

    public fun testingVariables(variable: String): String? {
        val entry = mapValuesAndVariables.entries.find { it.key.first == variable }
        return entry?.value
    }
}
