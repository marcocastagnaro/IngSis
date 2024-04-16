package org.example.validator

import org.example.AbstractSyntaxTree
import org.example.strategies.VariableToken

interface ValidationRule {
    fun validate(
        input: AbstractSyntaxTree,
        variables: Map<VariableToken, String?>,
    ): Boolean
}
