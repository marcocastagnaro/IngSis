package org.example

import org.example.Token.Position

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
