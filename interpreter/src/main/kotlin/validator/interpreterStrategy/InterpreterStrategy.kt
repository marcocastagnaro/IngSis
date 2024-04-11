package org.example.validator.interpreterStrategy

import org.example.AbstractSyntaxTree
import org.example.VariableToken

interface InterpreterStrategy {
    fun interpret(
        tree: AbstractSyntaxTree,
        variables: Map<VariableToken, String>,
    ): Map<VariableToken, String>
}
