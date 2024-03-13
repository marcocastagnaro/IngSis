package org.example.lexer

import org.example.Token.Token
import org.example.Token.Types

class ValueMapper{

    private val regexMap = mapOf(
        "(if|else|for|while|when|fun|class|object|return|break|continue|val|var)".toRegex() to Types.KEYWORD,
        "(\\d+|\"[^\"]*\"|'[^']*')".toRegex() to Types.LITERAL,
        """(?<!['"])[a-zA-Z][a-zA-Z0-9]*(?!['"])""".toRegex() to Types.IDENTIFIER,
        "[,;(){}\\[\\]].*".toRegex() to Types.PUNCTUATOR,
        "[+\\-*/%=><!&|^~]*".toRegex() to Types.OPERATOR,
        "(//.*|/\\*(.|\\n)*?\\*/)".toRegex() to Types.COMMENT
    )


    fun assigningTypesToTokenValues(tokenList : List<SplitToken>) : List<Token>{
        val resultTokens = ArrayList<Token>()
        for (token in tokenList) {
            for ((key, type) in regexMap) {
                val result = key.find(token.getValue())
                if (result != null){
                    resultTokens.add(createToken(token, type))
                    break
                }
            }
        }
        return resultTokens
    }

    fun createToken(value : SplitToken, type: Types) : Token{
        return Token(type,value.getValue(), value.getInitialPosition(), value.getFinalPosition())
    }
}