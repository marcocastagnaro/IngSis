package org.example.sca

import org.example.AbstractSyntaxTree
import output.Output

interface Sca {
    fun readJson(rulesPath: String)

    fun check(trees: List<AbstractSyntaxTree>): Output
}
