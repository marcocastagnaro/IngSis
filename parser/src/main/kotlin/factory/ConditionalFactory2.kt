package org.example.factory

import org.example.*

class ConditionalFactory2 : ASTFactory {

    val conditionalState = ConditionalState(-1, -1)
    val parser = Parser()

    override fun createAST(tokens: List<Token>): AbstractSyntaxTree {
        val root = NodeBuilder()
        var index = 0
        for (token in tokens) {
            when (token.getType()) {
                Types.CONDITIONAL -> handleConditionalType(tokens, index, root)
                Types.PUNCTUATOR -> handlePunctuatorType(tokens, index, root)
                else ->

            }
            index++
        }
    }



    private fun handleConditionalType(tokens: List<Token>, index: Int, root: NodeBuilder) {
        val conditionToken = tokens[index + 1]
        if (conditionToken.getValue() == "if") {
            conditionalState.indexEnteringIf = index + 5
        } else {
            conditionalState.indexEnteringElse = index + 5
        }
        root.setValue(conditionToken)
    }

    private fun handlePunctuatorType(tokens: List<Token>, index: Int, root: NodeBuilder) {
        val punctuator = tokens[index].getValue()
        if (punctuator == "}") {
            if (conditionalState.indexEnteringIf != -1) {
                root.setLeft(parser.execute(tokens.slice(conditionalState.indexEnteringIf until index)))
                conditionalState.indexEnteringIf = -1
            } else {
                root.setRight(parser.execute(tokens.slice(conditionalState.indexEnteringElse until index)))
                conditionalState.indexEnteringElse = -1
            }
        }
    }

    private fun handleBuildBlock(tokens: List<Token>, index: Int, root: NodeBuilder) {
        val block = tokens.slice(index + 1 until tokens.size)
        for (token in block) {
            val parsed = parser.execute(listOf(token))
        }
    }

    private fun getTreesFromList(trees: List<AbstractSyntaxTree>): AbstractSyntaxTree {
        if (trees.size == 1) {
            return trees[0]
        }
        val root = CompositeAbstractSyntaxTree(trees[0].getToken())
        root.setLeft(trees[0])
        root.setRight(getTreesFromList(trees.slice(1 until trees.size)))
        return root
    }



    override fun canHandle(tokens: List<Token>): Boolean {
        return tokens.find { it.getType() == Types.CONDITIONAL } != null
    }

}


class ConditionalState(
    var indexEnteringIf: Int,
    var indexEnteringElse: Int
)