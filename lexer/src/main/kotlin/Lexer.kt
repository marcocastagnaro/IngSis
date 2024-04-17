package org.example

import org.example.splittingStrategy.SplittingState
import org.example.splittingStrategy.StrategyMapper

class Lexer(version: String, private val splitStrategyMapper: StrategyMapper = StrategyMapper()) {
    private var map: ValueMapper = ValueMapper(version)

    fun execute(string: String): List<Token> {
        val updatedString = formatString(string)
        val rows = splitRows(updatedString)
        val tokens = ArrayList<SplitToken>()
        for ((index, row) in rows.withIndex()) {
            if (row.isBlank()) continue
            tokens.addAll(splitRow(row, index))
        }
        return map.assigningTypesToTokenValues(tokens)
    }

    private fun formatString(string: String) = string.replace("\r", "")

    private fun splitRows(string: String): List<String> {
        return string.split("(?<=;)(?=\\R|$)".toRegex())
    }

    private fun splitRow(
        string: String,
        row: Int,
    ): List<SplitToken> {
        val tokens = ArrayList<SplitToken>()
        val splittingState = SplittingState(-1, false, false)
        string.forEachIndexed { index, char ->
            val strategy = splitStrategyMapper.getStrategy(char.toString())
            val chart = char.toString()
            if (strategy != null) {
                strategy.split(string, row, tokens, splittingState, index, char)
                if (char == '"') {
                    splittingState.readingString = !splittingState.readingString
                }
            }
        }
        return tokens
    }
}
