package org.example.sca

import org.example.AbstractSyntaxTree

interface Sca {

    fun readJson(json: List<String>)

    fun applyUpRules(trees: List<AbstractSyntaxTree>)


}