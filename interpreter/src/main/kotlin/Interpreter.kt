package org.example

import org.example.inputReader.InputReaderType
import org.example.inputReader.ReadInputFromTerminal

class Interpreter(private val inputReader: InputReaderType = ReadInputFromTerminal()) {
    private val variables = HashMap<VariableToken, String?>()
    private val output: Output = Output()

    fun execute(trees: List<AbstractSyntaxTree>): Output {
        for (tree in trees) {
            when (tree.getToken().getType()) {
                Types.KEYWORD -> executeDeclaration(tree)
                Types.ASSIGNATION -> executeAssignation(tree)
                Types.FUNCTION -> executePrint(tree)
                else -> continue
            }
        }
        return output
    }

    private fun executeAssignation(tree: AbstractSyntaxTree) {
        variables.putAll(AssignationInterpreter(inputReader).interpret(tree, variables))
    }

    private fun executeDeclaration(tree: AbstractSyntaxTree) {
        variables.putAll(DeclarationInterpreter().interpret(tree, variables))
    }

    private fun executePrint(tree: AbstractSyntaxTree) {
        val mapResult = PrintInterpreter(inputReader).interpret(tree, variables).entries.first().value
        output.buildOutput(mapResult)
    }
}
