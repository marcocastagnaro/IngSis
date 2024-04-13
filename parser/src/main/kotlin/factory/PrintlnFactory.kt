package org.example

import org.example.factory.OperationFactory

class PrintlnFactory : ASTFactory {
    override fun createAST(tokens: List<Token>): AbstractSyntaxTree {
        val root = PrintNode(tokens.find { it.getValue() == "println" }!!)
        root.setRight(getSumPrintln(tokens.drop(2).dropLast(1)))
        return root
    }

    override fun canHandle(tokens: List<Token>): Boolean {
        return tokens.any { it.getType() == Types.FUNCTION }
    }

    private fun getSumPrintln(
        tokens: List<Token>,
        root: NodeBuilder? = null,
    ): AbstractSyntaxTree {
        val sumIndex =
            tokens.indexOfFirst { it.getValue() == "+" || it.getValue() == "-" || it.getValue() == "*" || it.getValue() == "/" }

        return if (sumIndex == -1) {
            root?.setRight(NodeBuilder().setValue(tokens[0]).build())?.build() ?: NodeBuilder().setValue(tokens[0]).build()
        } else {
           OperationFactory().createAST(tokens).also { root?.setRight(it) ?: it }
        }
    }
}
