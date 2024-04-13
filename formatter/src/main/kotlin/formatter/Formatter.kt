package org.example.formatter

import org.example.AbstractSyntaxTree
import org.example.ParseTreeToTokens
import org.example.Token

class Formatter(val formatPath: String = "src/main/resources/formatter/StandardRules.json") {
    private val formatRules: List<FormatRule> = JsonDeserialization().getRulesFromJson(formatPath)

    val mandatoryRules =
        listOf(
            EnforceSpaces(1),
            AddBrackets(),
            SemicolonRule(),
        )

    fun execute(trees: List<AbstractSyntaxTree>): String {
        val formattedCode = mutableListOf<String>()
        for (tree in trees) {
            val tokens = ParseTreeToTokens().parseToTokens(tree)
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
