package org.example.sca

import org.example.AbstractSyntaxTree
import org.example.rules.RuleFactory
import org.example.rules.Rules
import org.example.Token
import org.example.formatter.ParseTreeToTokens


class ScaImpl : Sca {
    private var rules : List<Rules> = listOf()

    private fun setUpRules(json: List<String>) {
        var ruleFactory = RuleFactory()
        for (rule in json) {
            var rule = ruleFactory.createRule(rule)
            rules.addLast(rule)
        }
    }

    private fun createTokens(trees: List<AbstractSyntaxTree>): List<List<Token>> {
        var tokens = mutableListOf<List<Token>>()
        for (tree in trees) {
            val tokens_for_tree = ParseTreeToTokens().parseToString(tree)
            tokens.add(tokens_for_tree)
        }
        return tokens
    }

    override fun readJson(json: List<String>) {
        rules = listOf<Rules>()
        this.setUpRules(json)
        }

    override fun applyUpRules(trees: List<AbstractSyntaxTree>) {
        var tokens = createTokens(trees)
        for (rule in rules) {
            rule.applyRule(tokens)
        }
    }





}



