package org.example.staticCodeeAnalyzer

import org.example.AbstractSyntaxTree
import org.example.ParseTreeToTokens
import org.example.Token
import java.io.File

class ScaImpl(private var version: ScaVersion) : Sca {
    private var rules: List<Rules> = listOf()
    private val jsonReader = JsonReader()
    private val rulesFactory = RulesFactory()

    private fun createTokens(trees: List<AbstractSyntaxTree>): List<List<Token>> {
        val tokens = mutableListOf<List<Token>>()
        for (tree in trees) {
            val tokensForTree = ParseTreeToTokens().parseToTokens(tree)
            tokens.add(tokensForTree)
        }
        return tokens
    }

    fun getVersion(): ScaVersion {
        return version
    }

    override fun readJson(rulesPath: String) {
        var jsonRules = jsonReader.getRulesFromJson(rulesPath)
        rules = rulesFactory.createRules(jsonRules, version)
    }

    override fun check(trees: List<AbstractSyntaxTree>): SCAOutput {
        var tokens = createTokens(trees)
        var brokenRules = mutableListOf<BrokenRule>()
        var scaOutput = SCAOutput()
        for (rule in rules) {
            getBrkRls(rule, tokens, brokenRules)
        }
        if (brokenRules.isNotEmpty()) {
            for (brokenRule in brokenRules) {
                scaOutput.addBrokenRule(brokenRule)
            }
        }
//        val txtContent = generateTxtContent(brokenRules)
//        val htmlContent = generateHtmlContent(brokenRules)
//        writeReports(txtContent, htmlContent)
        return scaOutput
    }

    private fun getBrkRls(
        applyRule: Rules,
        tokens: List<List<Token>>,
        brokenRules: MutableList<BrokenRule>,
    ) {
        val brkRules = applyRule.applyRule(tokens)
        for (brkRule in brkRules) {
            brokenRules.addLast(brkRule)
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
