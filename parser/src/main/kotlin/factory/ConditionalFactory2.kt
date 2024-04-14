package org.example.factory

import org.example.ASTFactory
import org.example.AbstractSyntaxTree
import org.example.CompositeAbstractSyntaxTree
import org.example.ConditionalBuilder
import org.example.ConditionalLeaf
import org.example.Parser
import org.example.Token
import org.example.Types

class ConditionalFactory2 : ASTFactory {
    private val conditionalState = ConditionalState(-1, -1)

    override fun createAST(tokens: List<Token>): AbstractSyntaxTree {
        val parser = Parser()
        val rootConditional = ConditionalBuilder()

        for (index in tokens.indices) {
            val token = tokens[index]
            when (token.getType()) {
                Types.CONDITIONAL -> handleConditionalType(tokens, index, rootConditional)
                Types.PUNCTUATOR -> handlePunctuatorType(tokens, index, rootConditional, parser)
                else -> continue
            }
        }
        return rootConditional.build()
    }

    private fun handleConditionalType(
        tokens: List<Token>,
        index: Int,
        root: ConditionalBuilder,
    ) {
        val conditionToken = tokens[index]
        if (tokens[index].getValue() == "if") {
            conditionalState.indexEnteringIf = index + 5
            root.setToken(tokens[index + 2])
        } else {
            conditionalState.indexEnteringElse = index + 5
        }
    }

    private fun handlePunctuatorType(
        tokens: List<Token>,
        index: Int,
        root: ConditionalBuilder,
        parser: Parser,
    ) {
        val punctuator = tokens[index].getValue()
        if (punctuator == "}") {
            if (conditionalState.indexEnteringIf != -1) {
                val conditionalToken = tokens[conditionalState.indexEnteringIf - 5]
                val listOfTrees = parser.execute(tokens.slice(conditionalState.indexEnteringIf until index))
                val ifTree =
                    CompositeAbstractSyntaxTree(
                        tokens[conditionalState.indexEnteringIf - 5],
                        null,
                        ConditionalLeaf(conditionalToken, listOfTrees),
                    )
                root.setLeft(ifTree)
                conditionalState.indexEnteringIf = -1
            } else {
                val conditionalToken = tokens[conditionalState.indexEnteringElse - 5]
                val listOfTrees = parser.execute(tokens.slice(conditionalState.indexEnteringElse until index))
                val elseTree =
                    CompositeAbstractSyntaxTree(
                        tokens[conditionalState.indexEnteringElse - 5],
                        null,
                        ConditionalLeaf(conditionalToken, listOfTrees),
                    )
                root.setRight(elseTree)
                conditionalState.indexEnteringElse = -1
            }
        }
    }

    override fun canHandle(tokens: List<Token>): Boolean {
        return tokens.find { it.getType() == Types.CONDITIONAL } != null
    }
}

class ConditionalState(
    var indexEnteringIf: Int,
    var indexEnteringElse: Int,
)
