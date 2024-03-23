package org.example.splittingStrategy

class StrategyMapper {
    private val splittingStrategies =
        mapOf(
            ' ' to SpaceSplittingStrategy(),
            '"' to QuoteSplittingStrategy(),
            ':' to SpecialChartSplittingStrategy(),
            ';' to SpecialChartSplittingStrategy(),
            ')' to FunctionSplittingStrategy(),
        )

    fun getStrategy(char: Char): SplittingStrategy? {
        return splittingStrategies[char]
    }
}
