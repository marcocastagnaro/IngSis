package org.example.sca

import org.example.AbstractSyntaxTree
import org.example.ParseTreeToTokens
import org.example.Token
import org.example.brokenRule.BrokenRule
import org.example.rules.Rules
import sca.JsonReader
import java.io.File

class ScaImpl : Sca {
    private var rules: List<Rules> = listOf()
    private val jsonReader = JsonReader()

    private fun createTokens(trees: List<AbstractSyntaxTree>): List<List<Token>> {
        var tokens = mutableListOf<List<Token>>()
        for (tree in trees) {
            val tokensForTree = ParseTreeToTokens().parseToString(tree)
            tokens.add(tokensForTree)
        }
        return tokens
    }

    override fun readJson(rulesPath: String) {
        rules = jsonReader.getRulesFromJson(rulesPath)
    }

    override fun check(trees: List<AbstractSyntaxTree>) {
        var tokens = createTokens(trees)
        var brokenRules = mutableListOf<BrokenRule>()
        for (rule in rules) {
            getBrkRls(rule.applyRule(tokens), brokenRules)
        }

        val txtContent = generateTxtContent(brokenRules)
        val htmlContent = generateHtmlContent(brokenRules)
        writeReports(txtContent, htmlContent)
    }

    private fun getBrkRls(
        applyRule: List<BrokenRule>,
        rules: MutableList<BrokenRule>,
    ) {
        for (brokenRule in applyRule) {
            rules.addLast(brokenRule)
        }
    }

    fun getRules(): List<Rules> {
        return rules
    }

    private fun writeToFile(
        content: String,
        filePath: String,
    ) {
        val file = File(filePath)
        file.writeText(content)
    }

    fun writeReports(
        txtContent: String,
        htmlContent: String,
    ) {
        writeToFile(txtContent, "src/main/resources/broken_rules_report.txt")
        writeToFile(htmlContent, "src/main/resources/broken_rules_report.html")
    }

    private fun generateTxtContent(brokenRules: List<BrokenRule>): String {
        val sb = StringBuilder()
        for (brokenRule in brokenRules) {
            sb.append("${brokenRule.getBrokenRule()} in line: ${brokenRule.getErrorPosition().getRow()}\n")
        }
        return sb.toString()
    }

    private fun generateHtmlContent(brokenRules: List<BrokenRule>): String {
        val sb = StringBuilder()
        sb.append("<html><head><title>Broken Rules Report</title></head><body><h1>Broken Rules</h1><ul>")
        for (brokenRule in brokenRules) {
            sb.append("<li>${brokenRule.getBrokenRule()} in line: ${brokenRule.getErrorPosition().getRow()}</li>")
        }
        sb.append("</ul></body></html>")
        return sb.toString()
    }
}
