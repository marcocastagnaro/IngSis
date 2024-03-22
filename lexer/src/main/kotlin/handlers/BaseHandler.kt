package org.example.handlers

import org.example.SplitToken
import org.example.Token.Position

abstract class BaseHandler : SeparationHandler {
    var handler: SeparationHandler? = null

    override fun setNext(separationHandler: SeparationHandler) {
        handler = separationHandler
    }

    override fun handle(
        string: String,
        separations: Map<String, Pair<Position, Position>>,
    ): List<SplitToken> {
        TODO("Not yet implemented")
    }

    fun createTokens(separations: Map<String, Pair<Position, Position>>): List<SplitToken> {
        val tokens = mutableListOf<SplitToken>()
        separations.forEach { (key, value) ->
            tokens.add(SplitToken(value.first, value.second, key))
        }
        return tokens
    }
}
