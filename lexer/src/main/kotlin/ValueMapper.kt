package org.example

class RegexAssignator(private val classification: Regex) : Assignator {
    override fun isThisType(splitToken: SplitToken): Boolean {
        return classification.matches(splitToken.getValue())
    }
}

interface Assignator {
    fun isThisType(splitToken: SplitToken): Boolean
}

class ValueMapper(private val version: String, assignatorTuple: List<Pair<Assignator, Types>>? = null) {
    companion object {
        val defaultVersion10 =
            listOf(
                RegexAssignator("(let)".toRegex()) to Types.KEYWORD,
                RegexAssignator("\\(|\\)".toRegex()) to Types.PARENTHESIS,
                RegexAssignator("println".toRegex()) to Types.FUNCTION,
                RegexAssignator("(string|number)".toRegex()) to Types.DATA_TYPE,
                RegexAssignator("(:)".toRegex()) to Types.DECLARATOR,
                RegexAssignator("(=)".toRegex()) to Types.ASSIGNATION,
                RegexAssignator("""(?:"([^"]*)"|'([^']*)'|(\d+(?:\.\d+)?))""".toRegex()) to Types.LITERAL,
                RegexAssignator("[,;{}\\[\\]\r\n].*".toRegex()) to Types.PUNCTUATOR,
                RegexAssignator("[+\\-*/%=><!&|^~]*".toRegex()) to Types.OPERATOR,
                RegexAssignator("""(?<!['"])[a-zA-Z][a-zA-Z0-9_]*(?!['"])""".toRegex()) to Types.IDENTIFIER,
            )

        val defaultVersion11 =
            listOf(
                RegexAssignator("(let|const)".toRegex()) to Types.KEYWORD,
                RegexAssignator("\\(|\\)".toRegex()) to Types.PARENTHESIS,
                RegexAssignator("if|else".toRegex()) to Types.CONDITIONAL,
                RegexAssignator("println|readInput".toRegex()) to Types.FUNCTION,
                RegexAssignator("(string|number|boolean)".toRegex()) to Types.DATA_TYPE,
                RegexAssignator("(true|false)".toRegex()) to Types.BOOLEAN,
                RegexAssignator("(readEnv)".toRegex()) to Types.READENV,
                RegexAssignator("(:)".toRegex()) to Types.DECLARATOR,
                RegexAssignator("(=)".toRegex()) to Types.ASSIGNATION,
                RegexAssignator("""(?:"([^"]*)"|'([^']*)'|(\d+(?:\.\d+)?))""".toRegex()) to Types.LITERAL,
                RegexAssignator("[,;{}\\[\\]\r\n].*".toRegex()) to Types.PUNCTUATOR,
                RegexAssignator("[+\\-*/%=><!&|^~]*".toRegex()) to Types.OPERATOR,
                RegexAssignator("""(?<!['"])[a-zA-Z][a-zA-Z0-9_]*(?!['"])""".toRegex()) to Types.IDENTIFIER,
            )
    }

    private val assignatorTuple: List<Pair<Assignator, Types>> =
        assignatorTuple ?: when (version) {
            "1.1" -> defaultVersion11
            else -> defaultVersion10
        }

    fun assigningTypesToTokenValues(tokenList: List<SplitToken>): List<Token> {
        val resultTokens = mutableListOf<Token>()
        tokenList.forEach { token ->
            val tokenType = findAppropriateTokenType(token)
            if (tokenType != null) {
                resultTokens.add(createToken(token, tokenType))
            }
        }
        return resultTokens
    }

    private fun findAppropriateTokenType(splitToken: SplitToken): Types? {
        assignatorTuple.forEach { (assignator, type) ->
            if (assignator.isThisType(splitToken)) {
                return type
            }
        }
        // If no appropriate type is found, return null
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
