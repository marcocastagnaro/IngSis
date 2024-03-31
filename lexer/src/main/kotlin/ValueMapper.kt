package org.example

class RegexAssignator(private val classification: Regex) : Assignator {
    override fun isThisType(splitToken: SplitToken): Boolean {
        return classification.matches(splitToken.getValue())
    }
}

interface Assignator {
    fun isThisType(splitToken: SplitToken): Boolean
}

class ValueMapper(private val assignatorTuple: List<Pair<Assignator, Types>> = default) {
    companion object {
        val default =
            listOf(
                RegexAssignator("(if|else|for|while|when|fun|class|object|return|break|continue|let)".toRegex()) to Types.KEYWORD,
                RegexAssignator("println".toRegex()) to Types.FUNCTION,
                RegexAssignator("(string|number)".toRegex()) to Types.DATA_TYPE,
                RegexAssignator("(:)".toRegex()) to Types.ASSIGNATOR,
                RegexAssignator("(\\d+|\"[^\"]*\"|'[^']*')".toRegex()) to Types.LITERAL,
                RegexAssignator("[,;(){}\\[\\]].*".toRegex()) to Types.PUNCTUATOR,
                RegexAssignator("[+\\-*/%=><!&|^~]*".toRegex()) to Types.OPERATOR,
                RegexAssignator("""(?<!['"])[a-zA-Z][a-zA-Z0-9]*(?!['"])""".toRegex()) to Types.IDENTIFIER,
                RegexAssignator("(//.*|/\\*(.|\\n)*?\\*/)".toRegex()) to Types.COMMENT,
            )
    }

    fun assigningTypesToTokenValues(tokenList: List<SplitToken>): List<Token> {
        val resultTokens = ArrayList<Token>()
        tokenList.forEach { token ->
            val tokenType = findAppropiateTokenType(token)
            if (tokenType != null) {
                resultTokens.add(createToken(token, tokenType))
            }
        }
        return resultTokens
    }

    private fun findAppropiateTokenType(splitToken: SplitToken): Types? {
        assignatorTuple.forEach { (assignator, type) ->
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
