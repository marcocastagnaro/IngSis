package org.example.staticCodeeAnalyzer
import org.example.Position

class BrokenRule {
    private val brokenRule: String
    private val errorPosition: Position

    constructor(rule: String, position: Position) {
        this.brokenRule = rule
        this.errorPosition = position
    }

    fun getBrokenRule(): String {
        return brokenRule
    }

    fun getErrorPosition(): Position {
        return errorPosition
    }
}
