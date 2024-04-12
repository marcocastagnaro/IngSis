package org.example

interface InterpreterStrategy {
    fun interpret(
        tree: AbstractSyntaxTree,
        variables: HashMap<VariableToken, String?>,
    ): Map<VariableToken, String?>
}
