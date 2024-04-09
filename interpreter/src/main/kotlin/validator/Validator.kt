package org.example.validator

import org.example.AbstractSyntaxTree
import org.example.validator.rules.DeclarationMatchesAssignationRule

class Validator(private val rules: List<ValidationRule> = default) {
    companion object {
        val default = listOf<ValidationRule>(DeclarationMatchesAssignationRule())
    }

    fun validate(input: List<AbstractSyntaxTree>): Boolean {
        for (tree in input){
            rules.forEach({ if (it.validate(tree) == false) return false }  )
        }
        return true
    }
}