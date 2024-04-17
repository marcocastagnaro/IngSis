package org.example.parser.factory

import org.example.AbstractSyntaxTree
import org.example.NodeBuilder
import org.example.PrintNode
import org.example.Token
import org.example.Types
import org.example.parser.ASTFactory

class PrintlnFactory : ASTFactory {
    override fun createAST(tokens: List<Token>): AbstractSyntaxTree {
        val root = PrintNode(tokens.find { it.getValue() == "println" }!!)
        root.setRight(getSumPrintln(tokens.drop(2).dropLast(1)))
        return root
    }

    override fun canHandle(tokens: List<Token>): Boolean {
        return tokens.any { it.getValue() == "println" }
    }

    private fun getSumPrintln(
        tokens: List<Token>,
        root: NodeBuilder? = null,
    ): AbstractSyntaxTree {
        if (firstValueIsFunction(tokens)) {
            val functionNode = FunctionFactory().createAST(tokens)
            if (root != null) {
                root.setRight(functionNode)
            } else {
                return functionNode
            }
        } else {
            return manageOperation(tokens, root)
        }
        return root.build()
    }

    private fun manageOperation(
        tokens: List<Token>,
        root: NodeBuilder?,
    ): AbstractSyntaxTree {
        val sumIndex = getOperationToken(tokens)
        return if (sumIndex == -1) {
            root?.setRight(NodeBuilder().setValue(tokens[0]).build())?.build()
                ?: NodeBuilder().setValue(tokens[0]).build()
        } else {
            OperationFactory().createAST(tokens).also {
                root?.setRight(it)
            }
        }
    }

    private fun getOperationToken(tokens: List<Token>) =
        tokens.indexOfFirst { it.getValue() == "+" || it.getValue() == "-" || it.getValue() == "*" || it.getValue() == "/" }

    private fun firstValueIsFunction(tokens: List<Token>) = tokens[0].getType() === Types.FUNCTION
}
