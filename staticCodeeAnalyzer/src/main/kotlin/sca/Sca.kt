package org.example.sca

import org.example.AbstractSyntaxTree

interface Sca {
    fun readJson(rulesPath: String)

    fun check(trees: List<AbstractSyntaxTree>)
}
