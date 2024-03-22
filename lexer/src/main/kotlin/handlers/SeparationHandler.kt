package org.example.handlers

import org.example.SplitToken
import org.example.Token.Position

interface SeparationHandler {
    fun setNext(separationHandler: SeparationHandler)

    fun handle(
        string: String,
        separations: Map<String, Pair<Position, Position>>,
    ): List<SplitToken>
}
