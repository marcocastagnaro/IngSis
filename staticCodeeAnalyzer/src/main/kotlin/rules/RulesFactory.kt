package rules

import org.example.rules.Rules
import org.example.rules.rulesImpls.CamelCase
import org.example.rules.rulesImpls.PrintWithoutExpresion
import org.example.rules.rulesImpls.SnakeCase
import rules.rulesImpls.ReadInputWithoutExpresion
import sca.ScaVersion

class RulesFactory() {
    fun createRules(
        jsonRules: List<Rules>,
        version: ScaVersion,
    ): List<Rules> {
        when (version) {
            ScaVersion.VERSION_1_0 -> return createVersion10(jsonRules)
            ScaVersion.VERSION_1_1 -> return createVersion11(jsonRules)
        }
    }

    private fun createVersion10(jsonRules: List<Rules>): List<Rules> {
        val listOfRules = mutableListOf<Rules>()
        for (rule in jsonRules) {
            when (rule.getRuleName().lowercase()) {
                "camelcase" -> listOfRules.add(CamelCase())
                "snakecase" -> listOfRules.add(SnakeCase())
                "printwithoutexpresion" -> listOfRules.add(PrintWithoutExpresion())
                else -> {
                    throw IllegalArgumentException("Rule not available for this version")
                }
            }
        }
        return listOfRules
    }

    private fun createVersion11(jsonRules: List<Rules>): List<Rules> {
        val listOfRules = mutableListOf<Rules>()
        for (rule in jsonRules) {
            when (rule.getRuleName().lowercase()) {
                "camelcase" -> listOfRules.add(CamelCase())
                "snakecase" -> listOfRules.add(SnakeCase())
                "printwithoutexpresion" -> listOfRules.add(PrintWithoutExpresion())
                "readinputwithoutexpresion" -> listOfRules.add(ReadInputWithoutExpresion())
                else -> {
                    throw IllegalArgumentException("Rule not available for this version")
                }
            }
        }
        return listOfRules
    }
}
