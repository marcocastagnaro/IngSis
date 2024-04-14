package org.example

import org.example.inputReader.InputReaderType
import org.example.inputReader.ReadInputFromTerminal
import org.example.strategies.*

class Interpreter(private val inputReader: InputReaderType = ReadInputFromTerminal()) {
    private val variables = HashMap<VariableToken, String?>()
    private val output: Output = Output()

    fun execute(trees: List<AbstractSyntaxTree>): Output {
        for (tree in trees) {
            when (tree.getToken().getType()) {
                Types.KEYWORD -> executeDeclaration(tree)
                Types.ASSIGNATION -> executeAssignation(tree)
                Types.FUNCTION -> executePrint(tree)
                Types.CONDITIONAL -> executeConditional(tree)
                else -> continue
            }
        }
        checkRemainingPrints()
        return output
    }

    private fun checkRemainingPrints() {
        val remainingPrints = variables.filter { it.key.type == TokenType.PRINT }
        for (print in remainingPrints) {
            print.value?.let { output.buildOutput(it) }
        }
    }



    private fun executeAssignation(tree: AbstractSyntaxTree) {
        variables.putAll(AssignationInterpreter(inputReader, output).interpret(tree, variables))
    }

    private fun executeDeclaration(tree: AbstractSyntaxTree) {
        variables.putAll(DeclarationInterpreter().interpret(tree, variables))
    }

    private fun executePrint(tree: AbstractSyntaxTree) {
        val mapResult = PrintInterpreter(inputReader, output).interpret(tree, variables).entries.first().value
        output.buildOutput(mapResult)
    }

    private fun executeConditional(tree: AbstractSyntaxTree) {
        variables.putAll(ConditionalInterpreter().interpret(tree, variables))
    }
}
