import org.example.*

class PrintlnASTfactory : ASTFactory {
    override fun createAST(tokens: List<Token>): AbstractSyntaxTree {
        val root = PrintNode(tokens.find { it.getValue() == "println" }!!)
        root.setRight(getSumPrintln(tokens.drop(2).dropLast(1)))
        return root
    }

    private fun getSumPrintln(tokens: List<Token>, root: NodeBuilder? = null): AbstractSyntaxTree {
        val sumIndex = tokens.indexOfFirst { it.getValue() == "+" }

        return if (sumIndex == -1) {
            root?.setRight(NodeBuilder().setValue(tokens[0]).build())?.build() ?: NodeBuilder().setValue(tokens[0]).build()
        } else {
            val currentRoot = root ?: NodeBuilder().setValue(tokens[sumIndex]).apply {
                setRight(getSumPrintln(tokens.drop(sumIndex + 1)))
            }
            val leftTokens = tokens.subList(0, sumIndex)
            val leftSubtree = getSumPrintln(leftTokens)
            currentRoot.setLeft(leftSubtree)
            currentRoot.build() as CompositeAbstractSyntaxTree
        }
    }
}
