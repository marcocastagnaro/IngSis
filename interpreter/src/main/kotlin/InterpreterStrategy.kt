package org.example

import org.example.strategies.VariableToken

interface InterpreterStrategy {
    fun interpret(
        tree: AbstractSyntaxTree,
        variables: HashMap<VariableToken, String?>,
        inmutableList : MutableList<String>
    ): Map<VariableToken, String?>
}
