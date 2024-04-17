package org.example.parser.factory

import org.example.AbstractSyntaxTree
import org.example.NodeBuilder
import org.example.Token
import org.example.Types

class FunctionFactory {
    fun createAST(tokens: List<Token>): AbstractSyntaxTree {
        val root = NodeBuilder()
        root.setValue(getFunctionToken(tokens))
        val rightTokens = getRightTokens(tokens)
        if (rightTokens.isEmpty()) return root.build()
        if (rightTokens.size > 1) {
            val left = OperationFactory().createAST(rightTokens)
            root.setRight(left)
        } else {
            root.setRight(NodeBuilder().setValue(rightTokens[0]).build())
        }
        return root.build()
    }

    private fun getRightTokens(tokens: List<Token>) = tokens.filter { it.getType() != Types.FUNCTION && it.getType() != Types.PARENTHESIS }

    private fun getFunctionToken(tokens: List<Token>) = tokens.find { it.getType() == Types.FUNCTION }!!
}
