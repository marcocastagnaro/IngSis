package org.example.splittingStrategy

import org.example.SplitToken
import org.example.SplitTokenBuilder.Companion.createToken

class SpecialChartSplittingStrategy : SplittingStrategy {
    override fun split(
        string: String,
        row: Int,
        tokens: MutableList<SplitToken>,
        state: SplittingState,
        index: Int,
        char: Char,
    ) {
        if (!state.readingString) {
            val wordStart = state.lastSpaceIndex + 1
            if (wordStart != index) {
                tokens.add(createToken(string.substring(wordStart, index), row, wordStart, index))
            }
            tokens.add(createToken(char.toString(), row, index, index))
            state.lastSpaceIndex = index
        }
    }
}
