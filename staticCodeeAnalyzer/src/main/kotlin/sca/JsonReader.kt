package sca

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.example.rules.Rules
import org.example.rules.rulesImpls.CamelCase
import org.example.rules.rulesImpls.PrintWithoutExpresion
import org.example.rules.rulesImpls.SnakeCase
import java.io.File

data class FormattingRules(
    val Identifier: String,
    val printwithoutexpresion: Boolean,
)

class JsonReader {
    fun getRulesFromJson(path: String): List<Rules> {
        val mapper = jacksonObjectMapper()
        val file = File(path)
        val rules = mapper.readValue(file, FormattingRules::class.java)
        val listOfRules = mutableListOf<Rules>()
        when (rules.Identifier.lowercase()) {
            "camelcase" -> listOfRules.add(CamelCase())
            "snakecase" -> listOfRules.add(SnakeCase())
        }
        if (rules.printwithoutexpresion) {
            listOfRules.add(PrintWithoutExpresion())
        }

        return listOfRules
    }
}
