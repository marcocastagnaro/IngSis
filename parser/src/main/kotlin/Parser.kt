package org.example

import org.example.factory.ReadEnvFactory

class Parser {
    val factories = listOf(ReadEnvFactory(),PrintlnFactory(), DeclarationFactory(), AssignationFactory())

    fun execute(tokens: List<Token>): List<AbstractSyntaxTree> {
        val sameLineTokens = getSameLineTokens(tokens)
        val result = mutableListOf<AbstractSyntaxTree>()

        for (tokenList in sameLineTokens) {
            val astFactory = determineFactory(tokenList)
            if (astFactory != null) {
                result.add(astFactory.createAST(tokenList))
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
            if (token.getValue() != ";") {
                singleRow.add(token)
            } else {
                rows.add(singleRow)
                singleRow = mutableListOf()
            }
        }
        return rows
    }
}
