package org.example.factory

import org.example.AbstractSyntaxTree
import org.example.NodeBuilder
import org.example.Token
import org.example.Types

class FunctionFactory {
    fun createAST(tokens: List<Token>): AbstractSyntaxTree {
        val root = NodeBuilder()
        root.setValue(tokens.find { it.getType() == Types.FUNCTION }!!)
        val rightTokens = tokens.filter { it.getType() != Types.FUNCTION && it.getType() != Types.PUNCTUATOR }
        if (rightTokens.isEmpty()) return root.build()
        if (rightTokens.size > 1) {
            val left = OperationFactory().createAST(rightTokens)
            root.setRight(left)
        } else {
            root.setRight(NodeBuilder().setValue(rightTokens[0]).build())
        }
        return root.build()
    }
}
