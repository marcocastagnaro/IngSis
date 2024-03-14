package org.example.lexer

import org.example.Token.Token
import org.example.Token.Types

class ValueMapper {
    private val regexMap =
        mapOf(
            "(if|else|for|while|when|fun|class|object|return|break|continue|let)".toRegex() to Types.KEYWORD,
            "(string|number)".toRegex() to Types.DATA_TYPE,
            "(:)".toRegex() to Types.ASSIGNATOR,
            "(\\d+|\"[^\"]*\"|'[^']*')".toRegex() to Types.LITERAL,
            """(?<!['"])[a-zA-Z][a-zA-Z0-9]*(?!['"])""".toRegex() to Types.IDENTIFIER,
            "[,;(){}\\[\\]].*".toRegex() to Types.PUNCTUATOR,
            "[+\\-*/%=><!&|^~]*".toRegex() to Types.OPERATOR,
            "(//.*|/\\*(.|\\n)*?\\*/)".toRegex() to Types.COMMENT,
        )

    fun assigningTypesToTokenValues(tokenList: List<SplitToken>): List<Token> {
        val resultTokens = ArrayList<Token>()
        for (token in tokenList) {
            for ((key, type) in regexMap) {
                val result = key.find(token.getValue())
                if (result != null) {
                    resultTokens.add(createToken(token, type))
                    break
                }
            }
        }
        return resultTokens
    }

    private fun createToken(
        value: SplitToken,
        type: Types,
    ): Token {
        val finalValue =
            if (type != Types.LITERAL) {
                removeSpaces(value.getValue())
            } else {
                value.getValue()
            }
        return Token(type, finalValue, value.getInitialPosition(), value.getFinalPosition())
    }

    private fun removeSpaces(input: String): String {
        return input.replace("\\s".toRegex(), "")
    }
}
