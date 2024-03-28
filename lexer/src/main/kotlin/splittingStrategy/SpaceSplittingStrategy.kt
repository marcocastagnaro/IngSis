package org.example.splittingStrategy

import org.example.SplitToken
import org.example.SplitTokenBuilder

class SpaceSplittingStrategy : SplittingStrategy {
    override fun split(
        string: String,
        row: Int,
        tokens: MutableList<SplitToken>,
        state: SplittingState,
        index: Int,
        char: Char,
    ) {
        if (!state.readingString) {
            if (index != state.lastSpaceIndex + 1) {
                val wordStart = state.lastSpaceIndex + 1
                tokens.add(SplitTokenBuilder.createToken(string.substring(wordStart, index), row, wordStart, index))
            }
            state.lastSpaceIndex = index
        }
    }
}
