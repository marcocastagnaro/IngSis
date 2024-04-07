package org.example.rules

import org.example.rules.rulesImpls.CamelCase
import org.example.rules.rulesImpls.PrintWithoutExpresion
import org.example.rules.rulesImpls.SnakeCase

class RuleFactory {

    fun createRule(rule: String): Rules {
        return when(rule){
            "camelCase" -> CamelCase()
            "snakeCase" -> SnakeCase()
            "printWithoutExpresion" -> PrintWithoutExpresion()
            else -> throw IllegalArgumentException("Rule not found")
        }
    }


}