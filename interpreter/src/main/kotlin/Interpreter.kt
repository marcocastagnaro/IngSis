package org.example

import org.example.Token.Types

class Interpreter(private var trees: List<AbstractSyntaxTree>) {
    private val mapValuesAndVariables = mutableMapOf<String, String>()

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
            val value = mapValuesAndVariables[tree.getToken().getValue()]
            if (value == null) System.out.println("Error! Not Valid Variable")
            else output.buildOutput(value)
        }
            else if (tree.isLeaf()) output.buildOutput(tree.getToken().getValue())


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
        val variable = tree.getLeft()?.getToken()?.getValue()
        val rightValue = tree.getRight()?.getToken()?.getValue() // esto si es un string tipo x = "hola"
        // TODO : si es una suma o conjunto de numeros esto no es valido
        if (variable != null && rightValue != null) {
            mapValuesAndVariables[variable] = rightValue
        }
    }
}
