package org.example

class Formatter(private val formatRules: List<FormatRule> = default) {
    companion object {
        val default = listOf<FormatRule>()
    }

    val mandatoryRules =
        listOf<FormatRule>(
            AddSemicolonAndNewLinesRule(),
            EnforceSpaces(1),
        )

    fun execute(trees: List<AbstractSyntaxTree>): String {
        val formattedCode = mutableListOf<String>()
        for (tree in trees) {
            val tokens = ParseTreeToTokens().parseToString(tree)
            formattedCode.add(giveFormat(tokens))
        }
        return formattedCode.joinToString("")
    }

    private fun giveFormat(tokens: MutableList<Token>): String {
        var formattedTokens = tokens
        mandatoryRules.forEach {
            formattedTokens = it.applyRule(formattedTokens).toMutableList()
        }
        formatRules.forEach {
            formattedTokens = it.applyRule(formattedTokens).toMutableList()
        }
        return formattedTokens.joinToString("") { it.getValue() }
    }
}
