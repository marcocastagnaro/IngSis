package org.example.factory

import org.example.ASTFactory
import org.example.AbstractSyntaxTree
import org.example.CompositeAbstractSyntaxTree
import org.example.ConditionalBuilder
import org.example.ConditionalLeaf
import org.example.Parser
import org.example.Token
import org.example.Types

class ConditionalFactory2() : ASTFactory {
    private val conditionalState = ConditionalState(-1, -1)

    override fun createAST(tokens: List<Token>): AbstractSyntaxTree {
        val rootConditional = ConditionalBuilder()
        for (index in tokens.indices) {
            val token = tokens[index]
            when (token.getType()) {
                Types.CONDITIONAL -> handleConditionalType(tokens, index, rootConditional)
                Types.PUNCTUATOR -> handlePunctuatorType(tokens, index, rootConditional)
                else -> {
                    if (conditionalState.indexEnteringIf == -1 && conditionalState.indexEnteringElse == -1 &&
                        conditionalState.alreadyEntered
                    ) {
                        finishParsing(index, tokens, rootConditional)
                    } else {
                        continue
                    }
                }
            }
        }
        return rootConditional.build()
    }

    private fun finishParsing(
        index: Int,
        tokens: List<Token>,
        conditionalRoot: ConditionalBuilder,
    ) {
        val parser = Parser()
        var indexAux = index
        while (indexAux < tokens.size) {
            val token = tokens[indexAux]
            if (token.getValue() == "if" && token.getType() == Types.CONDITIONAL) {
                break
            }
            indexAux++
        }
        conditionalState.alreadyEntered = false
        conditionalRoot.setBody(parser.execute(tokens.slice(index until indexAux)))
    }

    private fun handleConditionalType(
        tokens: List<Token>,
        index: Int,
        root: ConditionalBuilder,
    ) {
        val conditionToken = tokens[index]
        if (conditionToken.getValue() == "if") {
            conditionalState.indexEnteringIf = index + 5
            val token = tokens[index + 2]
            root.setToken(
                Token(
                    Types.CONDITIONAL,
                    token.getValue(),
                    token.getInitialPosition(),
                    token.getFinalPosition(),
                ),
            )
        } else {
            conditionalState.indexEnteringElse = index + 2
        }
    }

    private fun handlePunctuatorType(
        tokens: List<Token>,
        index: Int,
        root: ConditionalBuilder,
    ) {
        val parser = Parser()
        val punctuator = tokens[index].getValue()
        conditionalState.alreadyEntered = true
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
        return tokens.find { it.getType() == Types.CONDITIONAL && it.getValue() == "if" } != null
    }

}

class ConditionalState(
    var indexEnteringIf: Int,
    var indexEnteringElse: Int,
    var alreadyEntered: Boolean = false,
)
