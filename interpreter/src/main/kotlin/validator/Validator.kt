package org.example.validator

import org.example.AbstractSyntaxTree
import org.example.strategies.VariableToken
import org.example.validator.rules.DeclarationMatchesAssignationRule

class Validator(private val rules: List<ValidationRule> = default) {
    companion object {
        val default = listOf<ValidationRule>(DeclarationMatchesAssignationRule())
    }

    fun validate(
        input: List<AbstractSyntaxTree>,
        map: Map<VariableToken, String?>,
    ): Boolean {
        for (tree in input) {
            rules.forEach { if (!it.validate(tree, map)) return false }
        }
        return true
    }
}
