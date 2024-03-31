package org.example

class SplitToken(
    private val initialPosition: Position,
    private val finalPosition: Position,
    private val value: String,
) {
    fun getInitialPosition(): Position {
        return initialPosition
    }

    fun getValue(): String {
        return value
    }

    fun getFinalPosition(): Position {
        return finalPosition
    }
}
