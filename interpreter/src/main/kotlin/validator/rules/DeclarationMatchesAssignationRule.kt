package org.example.validator.rules

import org.example.AbstractSyntaxTree
import org.example.ParseTreeToTokens
import org.example.Token
import org.example.Types
import org.example.validator.ValidationRule

class DeclarationMatchesAssignationRule : ValidationRule {
    override fun validate(input: AbstractSyntaxTree): Boolean {
        val tokens = ParseTreeToTokens().parseToTokens(input)
        val declarationToken = tokens.find { it.getType() == Types.DATA_TYPE } ?: return true
        val valueList = tokens.filter { it.getType() == Types.LITERAL }
        if (valueList.isEmpty()) return true
        when (declarationToken.getValue()) {
            "number" -> {
                return checkValuesAreNumeric(valueList)
            }
            "string" -> {
                return checkAtLeastOneValueIsString(valueList)
            }
            else -> {
                return false
            }
        }
    }

    private fun checkAtLeastOneValueIsString(valueList: List<Token>): Boolean {
        return valueList.any { it.getValue().matches(Regex("(\\d+|\"[^\"]*\"|'[^']*')")) }
    }

    private fun checkValuesAreNumeric(valueList: List<Token>): Boolean {
        return valueList.all { it.getValue().matches(Regex("[0-9]+")) }
    }
}
