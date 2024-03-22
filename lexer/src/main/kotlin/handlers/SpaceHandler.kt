package org.example.handlers

import org.example.SplitToken
import org.example.Token.Position

class SpaceHandler(var separationHandler: SeparationHandler?) : BaseHandler() {
    override fun setNext(separationHandler: SeparationHandler) {
        this.separationHandler = separationHandler
    }

    override fun handle(
        string: String,
        separations: Map<String, Pair<Position, Position>>,
    ): List<SplitToken> {
        val newSeparations = mutableMapOf<String, Pair<Position, Position>>()
        var wordStart = 0
        var wordEnd = 0
        var word = ""

        for (character in string) {
            if (character == ' ') {
                if (word.isNotEmpty()) {
                    newSeparations[word] = Pair(Position(0, wordStart), Position(0, wordEnd - 1))
                    wordStart = wordEnd + 1
                    word = ""
                }
            } else {
                word.plus(character)
                wordEnd++
            }
        }

        if (separationHandler != null) {
            return separationHandler!!.handle(string, newSeparations)
        } else {
            return createTokens(newSeparations)
        }
    }
}
