package org.example.staticCodeeAnalyzer

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File

data class FormattingRules(
    val identifier: String,
    val printwithoutexpresion: Boolean,
    val readinputwithoutexpresion: Boolean,
)

class JsonReader {
    fun getRulesFromJson(path: String): List<Rules> {
        val mapper = jacksonObjectMapper()
        val file = File(path)
        val rules = mapper.readValue(file, FormattingRules::class.java)
        val listOfRules = mutableListOf<Rules>()
        when (rules.identifier.lowercase()) {
            "camelcase" -> listOfRules.add(CamelCase())
            "snakecase" -> listOfRules.add(SnakeCase())
        }
        if (rules.printwithoutexpresion) {
            listOfRules.add(PrintWithoutExpresion())
        }
        if (rules.readinputwithoutexpresion) {
            listOfRules.add(ReadInputWithoutExpresion())
        }

        return listOfRules
    }
}
