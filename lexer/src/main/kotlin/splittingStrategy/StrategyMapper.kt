package org.example.splittingStrategy

class StrategyMapper {
    private val splittingStrategies =
        mapOf(
            " " to SpaceSplittingStrategy(),
            "\"" to QuoteSplittingStrategy(),
            ":" to SpecialChartSplittingStrategy(),
            ";" to SpecialChartSplittingStrategy(),
            "(" to FunctionSplittingStrategy(),
            ")" to FunctionSplittingStrategy(),
            "+" to SpecialChartSplittingStrategy(),
            "-" to SpecialChartSplittingStrategy(),
            "*" to SpecialChartSplittingStrategy(),
            "/" to SpecialChartSplittingStrategy(),
        )

    fun getStrategy(char: String): SplittingStrategy? {
        return splittingStrategies[char]
    }
}
