package org.example

class PrintlnFactory : ASTFactory {
    override fun createAST(tokens: List<Token>): AbstractSyntaxTree {
        val root = PrintNode(tokens.find { it.getValue() == "println" }!!)
        root.setRight(getSumPrintln(tokens.drop(2).dropLast(1)))
        return root
    }

    override fun canHandle(tokens: List<Token>): Boolean {
        return tokens[0].getType() == Types.FUNCTION
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
            val currentRoot =
                root ?: NodeBuilder().setValue(tokens[sumIndex]).apply {
                    setRight(getSumPrintln(tokens.drop(sumIndex + 1)))
                }
            val leftTokens = tokens.subList(0, sumIndex)
            val leftSubtree = getSumPrintln(leftTokens)
            currentRoot.setLeft(leftSubtree)
            currentRoot.build() as CompositeAbstractSyntaxTree
        }
    }
}
