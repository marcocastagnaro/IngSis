package org.example.formatter

import org.example.AbstractSyntaxTree
import org.example.ParseToTokens
import org.example.ParseTreeToTokens
import org.example.Token

class Formatter(
    private val formatPath: String = "src/main/resources/formatter/StandardRules.json",
    val parser: ParseToTokens = ParseTreeToTokens(),
) {
    private val formatRules: List<FormatRule> = JsonDeserialization().getRulesFromJson(formatPath)

    private val mandatoryRules =
        listOf(
            EnforceSpaces(1),
            AddBrackets(),
            SemicolonRule(),
        )

    fun execute(trees: List<AbstractSyntaxTree>): String {
        val formattedCode = mutableListOf<String>()
        for (tree in trees) {
            val tokens = parser.parseToTokens(tree)
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
