package org.example.parser.factory

import org.example.AbstractSyntaxTree
import org.example.NodeBuilder
import org.example.Token
import org.example.Types
import org.example.parser.ASTFactory

class ReadEnvFactory : ASTFactory {
    override fun createAST(tokens: List<Token>): AbstractSyntaxTree {
        val root = NodeBuilder()
        if (tokens.find { it.getType() == Types.ASSIGNATION } == null) {
            root.setValue(tokens[0])
            root.setRight(NodeBuilder().setValue(tokens[2]).build())
        } else {
            root.setValue(tokens.find { it.getValue() == "=" }!!)
            val leftTokens = tokens.takeWhile { it.getValue() != "=" }
            val rightTokens = tokens.drop(leftTokens.size + 1)
            if (leftTokens.size > 1) {
                val left = variableDeclaration(leftTokens)
                root.setLeft(left)
            } else {
                root.setLeft(NodeBuilder().setValue(leftTokens[0]).build())
            }
            if (rightTokens.isEmpty()) {
                return root.build()
            }
// READENV, (, "",)
            if (rightTokens[0].getType() == Types.READENV) {
                val right = NodeBuilder()
                right.setValue(rightTokens[0])
                right.setRight(NodeBuilder().setValue(rightTokens[2]).build())
                root.setRight(right.build())
                return root.build()
            }
        }
        return root.build()
    }

    override fun canHandle(tokens: List<Token>): Boolean {
        return (tokens.any { it.getType() == Types.READENV })
    }

    private fun variableDeclaration(tokens: List<Token>): AbstractSyntaxTree {
        return DeclarationFactory().createAST(tokens)
    }
}
