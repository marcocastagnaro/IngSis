class SplitTokenBuilder {
    companion object {
        fun createToken(
            word: String,
            row: Int,
            firsCol: Int,
            lastCol: Int,
        ): SplitToken {
            val startingPos = Position(row, firsCol)
            val endingPos = Position(row, lastCol)
            return SplitToken(startingPos, endingPos, word)
        }
    }
}
