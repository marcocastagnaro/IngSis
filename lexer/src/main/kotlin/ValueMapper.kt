package org.example

import org.example.Token.Token
import org.example.Token.Types

class RegexAssignation(private val classification: Regex) : Assignation {
    override fun isThisType(splitToken: SplitToken): Boolean {
        return classification.matches(splitToken.getValue())
    }
}

interface Assignation {
    fun isThisType(splitToken: SplitToken): Boolean
}

class ValueMapper(private val assignationTuple: List<Pair<Assignation, Types>> = default) {
    companion object {
        val default =
            listOf(
                RegexAssignation("(if|else|for|while|when|fun|class|object|return|break|continue|let)".toRegex()) to Types.KEYWORD,
                RegexAssignation("(string|number)".toRegex()) to Types.DATA_TYPE,
                RegexAssignation("(:)".toRegex()) to Types.ASSIGNATOR,
                RegexAssignation("(\\d+|\"[^\"]*\"|'[^']*')".toRegex()) to Types.LITERAL,
                RegexAssignation("""(?<!['"])[a-zA-Z][a-zA-Z0-9]*(?!['"])""".toRegex()) to Types.IDENTIFIER,
                RegexAssignation("[,;(){}\\[\\]].*".toRegex()) to Types.PUNCTUATOR,
                RegexAssignation("[+\\-*/%=><!&|^~]*".toRegex()) to Types.OPERATOR,
                RegexAssignation("(//.*|/\\*(.|\\n)*?\\*/)".toRegex()) to Types.COMMENT,
            )
    }

    fun assigningTypesToTokenValues(tokenList: List<SplitToken>): List<Token> {
        val resultTokens = ArrayList<Token>()
        tokenList.forEach { token ->
            val tokenType = findAppropriateTokenType(token)
            if (tokenType != null) {
                resultTokens.add(createToken(token, tokenType))
            }
        }
        return resultTokens
    }

    private fun findAppropriateTokenType(splitToken: SplitToken): Types? {
        assignationTuple.forEach { (assignator, type) ->
            if (assignator.isThisType(splitToken)) {
                return type
            }
        }
        return null
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
