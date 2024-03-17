package org.example.interpreter

import org.example.AST.AbstractSyntaxTree
import org.example.Token.Types

class Interpreter(private var trees: List<AbstractSyntaxTree>) {
     val mapValuesAndVariables = mutableMapOf<String, String>()
    fun execute(): Output {
        val output : Output = Output()

        for (tree in trees)  {
            when (tree.getToken().getValue()) {
                "=" -> { // si es una asignacion. ASUMIENDO QUE NO HAY ERRORES
                    //TODO Falta el coso eso que busca errores y formatea
                    getValues(tree)
                }

                "println" -> { // si es un println
                    val value = tree.getLeft()?.getToken()?.getValue()
                    if (value != null) {
                        output.changeString(value)
                    }
                }
            }
        }
        return output
    }

    private fun getValues (tree : AbstractSyntaxTree) {//de izquierda a derecha
        val variable = tree.getLeft()?.getLeft()?.getToken()?.getValue()
        val rightValue = tree.getRight()?.getToken()?.getValue() //esto si es un string tipo x = "hola"
        //TODO : si es una suma o conjunto de numeros esto no es valido
        if (variable != null && rightValue != null) {
            mapValuesAndVariables[variable] = rightValue
        }
    }
}