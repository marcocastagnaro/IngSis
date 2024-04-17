package org.example.parser.factory

import org.example.AbstractSyntaxTree
import org.example.Leaf
import org.example.NodeBuilder
import org.example.Token

class OperationFactory {
    fun createAST(tokens: List<Token>): AbstractSyntaxTree {
        if (tokens.size == 1) {
            return Leaf(tokens[0])
        }
        for (token in tokens) {
            if (isAdditionOrSubstraction(token)) {
                val tree = NodeBuilder(value = token)
                tree.setLeft(createAST(tokens.subList(0, tokens.indexOf(token))))
                tree.setRight(createAST(tokens.subList(tokens.indexOf(token) + 1, tokens.size)))
                return tree.build()
            }
        }
        for (token in tokens) {
            if (isMultiplicationOrDivision(token)) {
                val tree = NodeBuilder(value = token)
                tree.setLeft(createAST(tokens.subList(0, tokens.indexOf(token))))
                tree.setRight(createAST(tokens.subList(tokens.indexOf(token) + 1, tokens.size)))
                return tree.build()
            }
        }
        throw Exception("Error in operation")
    }

    private fun isMultiplicationOrDivision(token: Token) = token.getValue() == "*" || token.getValue() == "/"

    private fun isAdditionOrSubstraction(token: Token) = token.getValue() == "+" || token.getValue() == "-"
}
