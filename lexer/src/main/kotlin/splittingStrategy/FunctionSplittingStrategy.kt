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
        if (!state.readingString || (state.readingString && state.insideFunction)) {
            val wordStart = state.lastSpaceIndex + 1
            /*if (!state.insideFunction) {
                tokens.add(SplitTokenBuilder.createToken(string.substring(wordStart, index), row, wordStart, index))
            }*/
            if (wordStart != index) {
                tokens.add(SplitTokenBuilder.createToken(string.substring(wordStart, index), row, wordStart, index))
            }
            tokens.add(SplitTokenBuilder.createToken(string.substring(index, index + 1), row, index, index))
            state.insideFunction = !state.insideFunction
            state.lastSpaceIndex = index
            state.readingString = false
        }
    }
}
