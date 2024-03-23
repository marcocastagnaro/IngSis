package org.example.splittingStrategy

import org.example.SplitToken
import org.example.SplitTokenBuilder

class FunctionSplittingStrategy : SplittingStrategy {
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
            tokens.add(SplitTokenBuilder.createToken(string.substring(wordStart, index + 1), row, wordStart, index))
            state.lastSpaceIndex = index
            state.readingString = false
        }
    }
}
