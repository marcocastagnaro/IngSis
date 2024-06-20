package org.example.staticCodeeAnalyzer

class SCAOutput {
    private var isOk: Boolean = true
    private var brokenRules: MutableList<String> = mutableListOf()

    fun isOk(): Boolean {
        return isOk
    }

    fun getBrokenRules(): List<String> {
        return brokenRules
    }

    private fun createRule(rule: BrokenRule): String {
        var position = rule.getErrorPosition()
        return "${rule.getBrokenRule()} at line ${position.getRow()}"
    }

    fun addBrokenRule(rule: BrokenRule) {
        isOk = false
        val ruleAsString = createRule(rule)
        brokenRules.add(ruleAsString)
    }
}
