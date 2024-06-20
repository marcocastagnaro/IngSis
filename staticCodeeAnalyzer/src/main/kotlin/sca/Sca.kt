package org.example.staticCodeeAnalyzer

import org.example.AbstractSyntaxTree

interface Sca {
    fun readJson(rulesPath: String)

    fun check(trees: List<AbstractSyntaxTree>): Output
}
