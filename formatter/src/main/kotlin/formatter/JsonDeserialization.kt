package org.example.formatter
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File

data class FormattingRules(
    val SpacesBeforeDeclaration: Boolean,
    val SpacesAfterDeclaration: Boolean,
    val SpacesInAssignation: Boolean,
    val NewLinesBeforePrintln: Int,
    val SpacesInIndentation: Int,
)

class JsonDeserialization {
    fun getRulesFromJson(path: String): List<FormatRule> {
        val mapper = jacksonObjectMapper()
        val file = File(path)
        val rules = mapper.readValue(file, FormattingRules::class.java)
        val listOfRules = mutableListOf<FormatRule>()
        listOfRules.add(SpacesInDeclarationRule(rules.SpacesBeforeDeclaration, rules.SpacesAfterDeclaration))
        listOfRules.add(ConditionalFormatter(rules.SpacesInIndentation))
        if (!rules.SpacesInAssignation) {
            listOfRules.add(NoSpacesInAssignationRule())
        }
        if (rules.NewLinesBeforePrintln > 0) {
            listOfRules.add(NewLinesBeforePrint(rules.NewLinesBeforePrintln))
        }
        return listOfRules
    }
}
