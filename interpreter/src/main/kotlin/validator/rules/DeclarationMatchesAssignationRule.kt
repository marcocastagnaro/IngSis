package org.example.validator.rules

import org.example.AbstractSyntaxTree
import org.example.ParseTreeToTokens
import org.example.Token
import org.example.Types
import org.example.strategies.VariableToken
import org.example.validator.ValidationRule

class DeclarationMatchesAssignationRule : ValidationRule {
    override fun validate(
        input: AbstractSyntaxTree,
        variables: Map<VariableToken, String?>,
    ): Boolean {
        val listOfTokens = getTokens(input)
        val symbol = getIdentifier(listOfTokens) ?: return true
        checkIsAssignation(listOfTokens) ?: return true
        if (checkIsNotFunction(listOfTokens)) return true
        val declarationToken = getAssginationToken(listOfTokens, symbol, variables)
        val values: List<String> = getAllValuesInAssignation(listOfTokens)
        if (declarationToken == null) throw Exception("Variable $symbol not declared")
        return validateAssignedValuesAreCorrect(declarationToken, values)
    }

    private fun validateAssignedValuesAreCorrect(
        declarationToken: String,
        values: List<String>,
    ): Boolean {
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

    private fun getAllValuesInAssignation(listOfTokens: MutableList<Token>) =
        listOfTokens.filter { it.getType() == Types.LITERAL || it.getType() == Types.BOOLEAN }.map {
            it.getValue()
        }

    private fun getAssginationToken(
        listOfTokens: MutableList<Token>,
        symbol: String,
        variables: Map<VariableToken, String?>,
    ) = listOfTokens.find { it.getType() == Types.DATA_TYPE }?.getValue() ?: findVariableInMap(symbol, variables)

    private fun checkIsNotFunction(listOfTokens: MutableList<Token>) =
        listOfTokens.any { it.getType() == Types.FUNCTION || it.getType() == Types.READENV }

    private fun checkIsAssignation(listOfTokens: MutableList<Token>) = listOfTokens.find { it.getType() == Types.ASSIGNATION }?.getValue()

    private fun getIdentifier(listOfTokens: MutableList<Token>) = listOfTokens.find { it.getType() == Types.IDENTIFIER }?.getValue()

    private fun getTokens(input: AbstractSyntaxTree) = ParseTreeToTokens().parseToTokens(input)

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
