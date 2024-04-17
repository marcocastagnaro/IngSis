package org.example.parser

import org.example.AbstractSyntaxTree
import org.example.Token
import org.example.parser.factory.AssignationFactory
import org.example.parser.factory.ConditionalFactory
import org.example.parser.factory.DeclarationFactory
import org.example.parser.factory.PrintlnFactory
import org.example.parser.factory.ReadEnvFactory

class Parser {
    val result = mutableListOf<AbstractSyntaxTree>()

    private val factories: List<ASTFactory> =
        listOf(
            ConditionalFactory(),
            PrintlnFactory(),
            ReadEnvFactory(),
            DeclarationFactory(),
            AssignationFactory(),
        )

    fun execute(tokens: List<Token>): List<AbstractSyntaxTree> {
        val sameLineTokens = getSameLineTokens(tokens)
        for (tokenList in sameLineTokens) {
            val astFactory = determineFactory(tokenList)
            if (astFactory != null) {
                if (astFactory is ConditionalFactory) {
                    return createConditionalAST(astFactory, tokens)
                } else {
                    result.add(astFactory.createAST(tokenList))
                }
            } else {
                throw Exception("Can't handle this sentence")
            }
        }
        return result
    }

    private fun createConditionalAST(
        astFactory: ConditionalFactory,
        tokens: List<Token>,
    ): MutableList<AbstractSyntaxTree> {
        val res = astFactory.createAST(tokens)
        result.add(res)
        if (res.getBody() != null) {
            res.getBody()!!.forEach(result::add)
        }
        return result
    }

    private fun determineFactory(tokens: List<Token>): ASTFactory? {
        for (factory in factories) {
            if (factory.canHandle(tokens)) {
                return factory
            }
        }
        return null
    }

    private fun getSameLineTokens(tokenList: List<Token>): List<List<Token>> {
        val rows = mutableListOf<List<Token>>()
        var singleRow = mutableListOf<Token>()
        for (token in tokenList) {
            if (token.getValue() != ";" && token.getValue() != "\n") {
                singleRow.add(token)
            } else {
                rows.add(singleRow)
                singleRow = mutableListOf()
            }
        }
        return rows
    }
}
