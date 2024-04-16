package org.example.validator.rules

import org.example.AbstractSyntaxTree
import org.example.ParseTreeToTokens
import org.example.Types
import org.example.strategies.VariableToken
import org.example.validator.ValidationRule

class DeclarationMatchesAssignationRule : ValidationRule {
    override fun validate(
        input: AbstractSyntaxTree,
        variables: Map<VariableToken, String?>,
    ): Boolean {
        val listOfTokens = ParseTreeToTokens().parseToTokens(input)
        val symbol = listOfTokens.find { it.getType() == Types.IDENTIFIER }?.getValue() ?: return true
        listOfTokens.find { it.getType() == Types.ASSIGNATION }?.getValue() ?: return true
        if (listOfTokens.any { it.getType() == Types.FUNCTION || it.getType() == Types.READENV }) return true
        val declarationToken = listOfTokens.find { it.getType() == Types.DATA_TYPE }?.getValue() ?: findVariableInMap(symbol, variables)
        val values: List<String> =
            listOfTokens.filter { it.getType() == Types.LITERAL || it.getType() == Types.BOOLEAN }.map {
                it.getValue()
            }
        if (declarationToken == null) throw Exception("Variable $symbol not declared")
        when (declarationToken) {
            "number" -> {
                return checkValuesAreNumeric(values)
            }
            "string" -> {
                return checkAtLeastOneValueIsString(values)
            }
            "boolean" -> {
                return checkValueIsBoolean(values)
            }
            else -> {
                return false
            }
        }
    }

    private fun checkValueIsBoolean(values: List<String>): Boolean {
        if (values.size > 1) return false
        return values.first().matches(Regex("true|false"))
    }

    fun checkAtLeastOneValueIsString(valueList: List<String>): Boolean {
        return valueList.any { it.matches(Regex("(\"[^\"]*\"|'[^']*')")) }
    }

    fun checkValuesAreNumeric(valueList: List<String>): Boolean {
        return valueList.all { it.matches(Regex("(\\d+(?:\\.\\d+)?)")) }
    }

    private fun findVariableInMap(
        symbol: String,
        map: Map<VariableToken, String?>,
    ): String? {
        return map.filter { it.key.value == symbol }.entries.firstOrNull()?.key?.type.toString().lowercase()
    }
}
