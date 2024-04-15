package org.example

import org.example.inputReader.InputReaderType
import org.example.inputReader.ReadInputFromTerminal
import org.example.strategies.AssignationInterpreter
import org.example.strategies.ConditionalInterpreter
import org.example.strategies.DeclarationInterpreter
import org.example.strategies.PrintInterpreter
import org.example.strategies.TokenType
import org.example.strategies.VariableToken

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
        output.removeLastNewLine()
        return output
    }

    private fun checkRemainingPrints() {
        val remainingPrints = variables.filter { it.key.type == TokenType.PRINT }
        val tempOutput = Output()
        for (print in remainingPrints) {
            print.value?.let { tempOutput.buildOutput(it + "\n") }
        }
        output.setOutput(tempOutput.string + output.string)
    }

    private fun executeAssignation(tree: AbstractSyntaxTree) {
        variables.putAll(AssignationInterpreter(inputReader, output).interpret(tree, variables))
    }

    private fun executeDeclaration(tree: AbstractSyntaxTree) {
        variables.putAll(DeclarationInterpreter().interpret(tree, variables))
    }

    private fun executePrint(tree: AbstractSyntaxTree) {
        val mapResult = PrintInterpreter(inputReader, output).interpret(tree, variables).entries.first().value
        output.buildOutput(mapResult + "\n")
    }

    private fun executeConditional(tree: AbstractSyntaxTree) {
        variables.putAll(ConditionalInterpreter(inputReader, output).interpret(tree, variables))
    }
}
