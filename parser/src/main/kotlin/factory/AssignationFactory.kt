package org.example

import org.example.factory.FunctionFactory
import org.example.factory.OperationFactory

class AssignationFactory : ASTFactory {
    override fun createAST(tokens: List<Token>): AbstractSyntaxTree {
        val root = NodeBuilder()
        root.setValue(tokens.find { it.getType() == Types.ASSIGNATION }!!)
        if (tokens.find { it.getType() == Types.KEYWORD } == null) {
            val leftTokens = tokens.takeWhile { it.getValue() != "=" }
            root.setLeft(NodeBuilder().setValue(leftTokens.first()).build()) // Agarro el primero, ya que va a ser un unico valor
            val rightTokens = tokens.drop(leftTokens.size + 1)
            if (rightTokens.size > 1) {
                val right = operationsDeclarator(rightTokens)
                root.setRight(right)
            } else {
                root.setRight(NodeBuilder().setValue(rightTokens[0]).build())
            }
            return root.build()
        } else {
            val root = NodeBuilder()
            root.setValue(tokens.find { it.getValue() == "=" }!!)
            val leftTokens = tokens.takeWhile { it.getValue() != "=" }
            val rightTokens = tokens.drop(leftTokens.size + 1)
            if (leftTokens.size > 1) {
                val left = variableDeclaration(leftTokens)
                root.setLeft(left)
            } else {
                root.setLeft(NodeBuilder().setValue(leftTokens[0]).build())
            }
            if (rightTokens.isEmpty()) {
                return root.build()
            }
            if (rightTokens.size > 1) {
                val right = createAssignatedTree(rightTokens)
                root.setRight(right)
            } else {
                root.setRight(NodeBuilder().setValue(rightTokens[0]).build())
            }
            return root.build()
        }
    }

    private fun createAssignatedTree(tokens: List<Token>): AbstractSyntaxTree {
        if (tokens.any { it.getType() == Types.FUNCTION }) {
            return FunctionFactory().createAST(tokens)
        }
        return operationsDeclarator(tokens)
    }

    private fun operationsDeclarator(tokens: List<Token>): AbstractSyntaxTree {
        return OperationFactory().createAST(tokens)
    }

    override fun canHandle(tokens: List<Token>): Boolean {
        return tokens.any { it.getType() == Types.ASSIGNATION }
    }

    private fun variableDeclaration(tokens: List<Token>): AbstractSyntaxTree {
        val declarationToken = tokens.find { it.getType() == Types.DECLARATOR }
        val root = NodeBuilder()
        if (declarationToken != null) {
            root.setValue(declarationToken)
        }
        val identifierToken = tokens.find { it.getType() == Types.IDENTIFIER }!!
        root.setLeft(NodeBuilder().setValue(identifierToken).build())
        val dataTypeToken = tokens.find { it.getType() == Types.DATA_TYPE }!!
        root.setRight(NodeBuilder().setValue(dataTypeToken).build())

        val realRoot = NodeBuilder()
        val letToken = tokens.find { it.getType() == Types.KEYWORD }
        if (letToken != null) {
            realRoot.setValue(letToken)
        }
        realRoot.setRight(root.build())
        return realRoot.build()
    }
}
