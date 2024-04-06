package org.example

import org.example.splittingStrategy.SplittingState
import org.example.splittingStrategy.StrategyMapper

class Lexer(private var map: ValueMapper, private val splitStrategyMapper: StrategyMapper = StrategyMapper()) {
    fun execute(string: String): List<Token> {
        val rows = splitRows(string)
        val tokens = ArrayList<SplitToken>()
        val si = false
        for ((index, row) in rows.withIndex()) {
            if (row.isBlank()) continue
            tokens.addAll(splitRow(row, index))
        }
        return map.assigningTypesToTokenValues(tokens)
    }

    private fun splitRows(string: String): List<String> {
        return string.split(";")
    }

    private fun splitRow(
        string: String,
        row: Int,
    ): List<SplitToken> {
        val tokens = ArrayList<SplitToken>()
        val splittingState = SplittingState(-1, false, false)
        string.forEachIndexed { index, char ->
            val strategy = splitStrategyMapper.getStrategy(char.toString())
            if (strategy != null) {
                strategy.split(string, row, tokens, splittingState, index, char)
                if (char == '"') {
                    splittingState.readingString = !splittingState.readingString
                }
            }
        }
        splitStrategyMapper.getStrategy(";")?.split(string, row, tokens, splittingState, string.length, ';')
        return tokens
    }
}
