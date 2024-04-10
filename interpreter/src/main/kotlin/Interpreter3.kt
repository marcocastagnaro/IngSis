package org.example

import org.example.validator.InterpreterStrategy.AssignationInterpreter


class Interpreter3{

    private val variables = HashMap<VariableToken, String>()
    private val output: Output = Output()
    private val assignationInterpreter = AssignationInterpreter()

    fun execute(trees: List<AbstractSyntaxTree>): Output {
        for (tree in trees) {
            when (tree.getToken().getType()) {
                Types.ASSIGNATION -> executeAssignation(tree)
                Types.FUNCTION -> interpretFunction(tree.getRight()!!)
                else -> continue
            }
        }
        return output
    }

    private fun executeAssignation(tree: AbstractSyntaxTree) {
        variables.putAll(assignationInterpreter.interpret(tree, variables))
    }



}

//let x: number = 20 + 50