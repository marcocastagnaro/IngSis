package org.example

import org.example.inputReader.InputReaderType
import org.example.inputReader.ReadInputFromTerminal
import org.example.strategies.AssignationInterpreter
import org.example.strategies.ConditionalInterpreter
import org.example.strategies.DeclarationInterpreter
import org.example.strategies.PrintInterpreter
import org.example.strategies.TokenType
import org.example.strategies.VariableToken
import org.example.validator.Validator

class Interpreter(private val inputReader: InputReaderType = ReadInputFromTerminal()) {
    private val variables = HashMap<VariableToken, String?>()
    private val output: Output = Output()
    private val inmutableList: MutableList<String> = mutableListOf()
    private val validator: Validator = Validator()

    fun execute(trees: List<AbstractSyntaxTree>): Output {
        interpretASTs(trees)
        validateCode(trees)
        formatOutput()
        return output
    }

    private fun formatOutput() {
        checkRemainingPrints()
        output.removeLastNewLine()
    }

    private fun validateCode(trees: List<AbstractSyntaxTree>) {
        if (!validator.validate(trees, variables)) throw Exception("Invalid syntax")
    }

    private fun interpretASTs(trees: List<AbstractSyntaxTree>) {
        output.setOutput("")
        for (tree in trees) {
            when (tree.getToken().getType()) {
                Types.KEYWORD -> executeDeclaration(tree)
                Types.ASSIGNATION -> executeAssignation(tree)
                Types.FUNCTION -> executePrint(tree)
                Types.CONDITIONAL -> executeConditional(tree)
                else -> continue
            }
        }
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
        variables.putAll(AssignationInterpreter(inputReader, output).interpret(tree, variables, inmutableList))
    }

    private fun executeDeclaration(tree: AbstractSyntaxTree) {
        variables.putAll(DeclarationInterpreter().interpret(tree, variables, inmutableList))
    }

    private fun executePrint(tree: AbstractSyntaxTree) {
        val mapResult = PrintInterpreter(inputReader, output).interpret(tree, variables, inmutableList).entries.first().value
        output.buildOutput(mapResult + "\n")
    }

    private fun executeConditional(tree: AbstractSyntaxTree) {
        variables.putAll(ConditionalInterpreter(inputReader, output).interpret(tree, variables, inmutableList))
    }
}
