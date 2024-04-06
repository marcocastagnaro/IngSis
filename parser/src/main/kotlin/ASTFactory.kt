package org.example

interface ASTFactory {
    fun createAST(tokens: List<Token>): AbstractSyntaxTree

    fun canHandle(tokens: List<Token>): Boolean
}
