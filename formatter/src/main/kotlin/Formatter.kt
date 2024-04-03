package org.example

class Formatter(private val formatRules: List<FormatRule> = default) {
    companion object {
        val default = listOf(ReadSpacesFormat(), ReadLinesFormat())
    }

    fun execute(input: AbstractSyntaxTree): String {
        val tokens = ParseTreeToTokens().parseToString(input)
        return giveFormat(tokens)
        // TODO falta agregar un lector de espacios y lineas
    }

    private fun giveFormat(tokens: List<Token>): String {
        var formattedTokens = tokens
        formatRules.forEach {
            formattedTokens = it.applyRule(formattedTokens)
        }
        return formattedTokens.joinToString("") { it.getValue() }
    }
}
