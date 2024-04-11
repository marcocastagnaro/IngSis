package org.example

interface InterpreterStrategy {
    fun interpret(
        tree: AbstractSyntaxTree,
        variables: Map<VariableToken, String>,
    ): Map<VariableToken, String>
}
