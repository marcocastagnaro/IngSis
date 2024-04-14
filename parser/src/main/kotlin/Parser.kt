package org.example

import org.example.factory.ConditionalFactory2
import org.example.factory.ReadEnvFactory

class Parser {
    private val factories = listOf(ConditionalFactory2(), ReadEnvFactory(), PrintlnFactory(), DeclarationFactory(), AssignationFactory())

    fun execute(tokens: List<Token>): List<AbstractSyntaxTree> {
        val sameLineTokens = getSameLineTokens(tokens)
         val result = mutableListOf<AbstractSyntaxTree>()
        for (tokenList in sameLineTokens) {
            val astFactory = determineFactory(tokenList)
            if (astFactory != null) {
                if (astFactory is ConditionalFactory2) {
                    result.add(astFactory.createAST(tokens))
                    return result
                } else {
                    result.add(astFactory.createAST(tokenList))
                }
            } else {
                println("Error ...") // TODO : throw exception
            }
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
