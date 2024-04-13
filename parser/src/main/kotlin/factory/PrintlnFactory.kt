package org.example

import org.example.factory.FunctionFactory
import org.example.factory.OperationFactory
import org.example.factory.ReadEnvFactory

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
        if (tokens[0].getType() === Types.FUNCTION) {
            val functionNode = FunctionFactory().createAST(tokens)
            if (root != null) {
                root.setRight(functionNode)
            } else {
                return functionNode
            }
        } else if (tokens[0].getType() === Types.READENV) {
            val readEnvNode = ReadEnvFactory().createAST(tokens)
            if (root != null) {
                root.setRight(readEnvNode)
            } else {
                return readEnvNode
            }
        } else {
            val sumIndex =
                tokens.indexOfFirst { it.getValue() == "+" || it.getValue() == "-" || it.getValue() == "*" || it.getValue() == "/" }
            return if (sumIndex == -1) {
                root?.setRight(NodeBuilder().setValue(tokens[0]).build())?.build()
                    ?: NodeBuilder().setValue(tokens[0]).build()
            } else {
                OperationFactory().createAST(tokens).also {
                    root?.setRight(it)
                }
            }
        }
        return root.build()
    }
}
