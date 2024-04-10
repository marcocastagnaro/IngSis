package org.example.validator

import org.example.AbstractSyntaxTree

interface ValidationRule {
    fun validate(input: AbstractSyntaxTree): Boolean
}
