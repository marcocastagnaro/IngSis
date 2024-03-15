package org.example.parser

import org.example.AST.AbstractSyntaxTree
import org.example.Token.Token
import org.example.Token.Types
import org.example.Token.nodes.CompositeNode
import org.example.Token.nodes.Leaf
import org.example.Token.nodes.Node

class Parser {
    fun execute(tokens: List<Token>) : List <AbstractSyntaxTree>{
        val finalList= ArrayList<AbstractSyntaxTree>();
        for (i in tokens.indices) {
            //AGARRO LOS TOKENS QUE ESTEN EN LA MISMA LINEA
            val sameLineTokens = getSameLineTokens(tokens, i)
            //PRIMER IF QUE CHEQUEA SI ES UNA ASSIGNATION
            if (sameLineTokens.isNotEmpty() && findAssignation(sameLineTokens)) {
                val abstractSyntaxTree = variableAssignation(tokens)
                finalList.add(abstractSyntaxTree)
            }
        }
        return finalList
    }
    private fun variableAssignation(tokens: List<Token>): AbstractSyntaxTree {
        val leftTokens = tokens.takeWhile { it.getValue() != "=" }
        val rightTokens = tokens.drop(leftTokens.size + 1)

        val identifierToken = leftTokens[1]
        val dataTypeToken = leftTokens.find { it.getType() == Types.DATA_TYPE }!!

        val leftAST = AbstractSyntaxTree(Token(Types.KEYWORD, "let", leftTokens.first().getInitialPosition(), leftTokens.first().getFinalPosition()), AbstractSyntaxTree(identifierToken), AbstractSyntaxTree(dataTypeToken))

        val valueToken = rightTokens.find { it.getType() == Types.LITERAL }!!
        val rightAST = AbstractSyntaxTree(valueToken)
        val equalToken : Token = tokens.find { it.getValue() == "=" }!!

        return AbstractSyntaxTree(equalToken, leftAST, rightAST)
    }
    private fun getSameLineTokens (tokens : List<Token>, row : Int) : List<Token> {
        val sameLineTokens = ArrayList<Token>();

        for (token in tokens){
            if (token.getInitialPosition().getRow() == row){
                sameLineTokens.add(token)
            }
        }
        return sameLineTokens;

    }
    private fun findAssignation(tokens: List<Token>) : Boolean {
        for (token in tokens){
            if (token.getValue() == "=") {
        return true;
            }
        }
        return false;
    }
}
