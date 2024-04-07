package org.example.brokenRule
import org.example.Position


class BrokenRule {
    private val brokenRule: String
    private val ErrorPosition: Position

    constructor(rule: String, position: Position) {
        this.brokenRule = rule
        this.ErrorPosition = position
    }

    fun getBrokenRule(): String {
        return brokenRule
    }

    fun getErrorPosition(): Position {
        return ErrorPosition
    }


}