package org.example

import org.example.validator.interpreterStrategy.AssignationInterpreter
import org.example.validator.interpreterStrategy.PrintInterpreter

class Interpreter3 {
    private val variables = HashMap<VariableToken, String>()
    private val output: Output = Output()

    fun execute(trees: List<AbstractSyntaxTree>): Output {
        for (tree in trees) {
            when (tree.getToken().getType()) {
                Types.ASSIGNATION -> executeAssignation(tree)
                Types.FUNCTION -> executePrint(tree)
                else -> continue
            }
        }
        return output
    }

    private fun executeAssignation(tree: AbstractSyntaxTree) {
        variables.putAll(AssignationInterpreter().interpret(tree, variables))
    }

    private fun executePrint(tree: AbstractSyntaxTree) {
        val mapResult = PrintInterpreter().interpret(tree, variables).entries.first().value
        output.buildOutput(mapResult)
    }
}
