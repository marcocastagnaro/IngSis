package org.example

class IfParserToTokens : ParseToTokens {
    override fun parseToTokens(root: AbstractSyntaxTree): MutableList<Token> {
        val tokenList: MutableList<Token> = mutableListOf()
        val isTree = checkConditional(root, tokenList)
        getTokens(root, tokenList)
        if (isTree) {
            swapTokens(tokenList)
        }
        return tokenList
    }

    private fun swapTokens(tokenList: MutableList<Token>) {
        val temp = tokenList[0]
        tokenList[0] = tokenList[1]
        tokenList[1] = temp
        tokenList.add(1, Token(Types.PUNCTUATOR, "(", tokenList[0].getFinalPosition(), tokenList[0].getFinalPosition()))
        tokenList.add(3, Token(Types.PUNCTUATOR, ")", tokenList[2].getInitialPosition(), tokenList[2].getInitialPosition()))
        val parenthesisToken = tokenList.find { it.getType() == Types.PARENTHESIS }!!
        val elseToken = Token(Types.CONDITIONAL, "else", parenthesisToken.getInitialPosition(), parenthesisToken.getInitialPosition())
        tokenList[tokenList.indexOf(parenthesisToken)] = elseToken
    }

    private fun checkConditional(
        root: AbstractSyntaxTree,
        tokenList: MutableList<Token>,
    ): Boolean {
        if (root.getToken().getType() == Types.CONDITIONAL) {
            tokenList.add(root.getToken())
            return true
        }
        return false
    }

    private fun getTokens(
        root: AbstractSyntaxTree,
        tokenList: MutableList<Token>,
    ) {
        when (root) {
            is ConditionalNode -> {
                getTokens(root.getLeft(), tokenList)
                if (root.getRight() != null) {
                    getTokens(root.getRight()!!, tokenList)
                }
                if (root.getBody() != null) {
                    root.getBody()!!.map { getTokens(it, tokenList) }
                }
            }
            is ConditionalLeaf -> {
                tokenList.add(root.getToken())
                tokenList.add(Token(Types.PUNCTUATOR, "{", root.getToken().getFinalPosition(), root.getToken().getFinalPosition()))
                root.getBody().map {
                    getTokens(it, tokenList)
                    tokenList.add(Token(Types.PUNCTUATOR, ";\n", root.getToken().getFinalPosition(), root.getToken().getFinalPosition()))
                }
                tokenList.add(Token(Types.PUNCTUATOR, "}", root.getToken().getFinalPosition(), root.getToken().getFinalPosition()))
            }
            else -> {
                if (root.getToken().getType() == Types.CONDITIONAL || root.getToken().getType() == Types.PARENTHESIS) {
                    if (root.getLeft() != null) getTokens(root.getLeft()!!, tokenList)
                    if (root.getRight() != null) getTokens(root.getRight()!!, tokenList)
                } else {
                    tokenList.addAll(ParseTreeToTokens().parseToTokens(root))
                }
            }
        }
    }
}
