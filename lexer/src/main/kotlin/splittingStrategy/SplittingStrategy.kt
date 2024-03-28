package splittingStrategy

import SplitToken

interface SplittingStrategy {
    fun split(
        string: String,
        row: Int,
        tokens: MutableList<SplitToken>,
        state: SplittingState,
        index: Int,
        char: Char,
    )
}
