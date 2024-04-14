package org.example

import org.example.strategies.VariableToken

interface InterpreterStrategy {
    fun interpret(
        tree: AbstractSyntaxTree,
        variables: HashMap<VariableToken, String?>,
    ): Map<VariableToken, String?>
}
