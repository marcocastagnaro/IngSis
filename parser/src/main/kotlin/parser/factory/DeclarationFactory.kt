package org.example.parser.factory

import org.example.AbstractSyntaxTree
import org.example.NodeBuilder
import org.example.Token
import org.example.Types
import org.example.parser.ASTFactory

class DeclarationFactory : ASTFactory {
    override fun createAST(tokens: List<Token>): AbstractSyntaxTree {
        val root = buildDeclaratorAST(tokens)
        return buildFinalAST(tokens, root)
    }

    private fun buildFinalAST(
        tokens: List<Token>,
        root: NodeBuilder,
    ): AbstractSyntaxTree {
        val realRoot = NodeBuilder()
        val letToken = getKeyword(tokens)
        if (letToken == null) {
            throw Exception("Keyword not found")
        }
        realRoot.setValue(letToken)

        realRoot.setRight(root.build())
        return realRoot.build()
    }

    private fun buildDeclaratorAST(tokens: List<Token>): NodeBuilder {
        val declarationToken = findDeclarator(tokens)
        val root = NodeBuilder()

        if (declarationToken == null) {
            throw Exception("Declarator not found")
        }
        root.setValue(declarationToken)

        val identifierToken = getIdentifierToken(tokens)
        root.setLeft(buildAST(identifierToken))
        val dataTypeToken = getDataTypeToken(tokens)
        root.setRight(buildAST(dataTypeToken))
        return root
    }

    private fun getKeyword(tokens: List<Token>) = tokens.find { it.getType() == Types.KEYWORD }

    private fun findDeclarator(tokens: List<Token>) = tokens.find { it.getType() == Types.DECLARATOR }

    private fun buildAST(identifierToken: Token) = NodeBuilder().setValue(identifierToken).build()

    private fun getDataTypeToken(tokens: List<Token>) = tokens.find { it.getType() == Types.DATA_TYPE }!!

    private fun getIdentifierToken(tokens: List<Token>) = tokens.find { it.getType() == Types.IDENTIFIER }!!

    override fun canHandle(tokens: List<Token>): Boolean {
        if (tokens.any { it.getType() == Types.ASSIGNATION }) {
            return false
        }
        if (tokens.any { it.getType() == Types.KEYWORD }) {
            return true
        }
        return false
    }
}
