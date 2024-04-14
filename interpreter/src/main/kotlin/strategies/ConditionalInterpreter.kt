package org.example.strategies

import org.example.AbstractSyntaxTree
import org.example.ConditionalLeaf
import org.example.InterpreterStrategy
import org.example.Types
import org.example.inputReader.ReadInputFromTerminal

class ConditionalInterpreter : InterpreterStrategy {
    override fun interpret(
        tree: AbstractSyntaxTree,
        variables: HashMap<VariableToken, String?>,
    ): Map<VariableToken, String?> {
        if (isConditionTrue(tree, variables)) {
            val body = tree.getLeft()!!.getRight() as ConditionalLeaf
            return solveBody(body, variables)
        } else {
            val body = tree.getRight()!!.getRight() as ConditionalLeaf
            return solveBody(body, variables)
        }
    }

    private fun isConditionTrue(
        tree: AbstractSyntaxTree,
        variables: HashMap<VariableToken, String?>,
    ): Boolean {
        if (variables.containsKey(VariableToken(tree.getToken().getValue(), TokenType.BOOLEAN))) {
            return variables[VariableToken(tree.getToken().getValue(), TokenType.BOOLEAN)] == "true"
        } else {
            throw IllegalArgumentException("Variable ${tree.getToken().getValue()} no declarada")
        }
    }

    private fun solveBody(
        tree: ConditionalLeaf,
        variables: HashMap<VariableToken, String?>,
    ): Map<VariableToken, String?> {
        val tempMap = HashMap(variables)
        for (subTree in tree.getBody()) {
            when (subTree.getToken().getType()) {
                Types.KEYWORD -> tempMap.putAll(DeclarationInterpreter().interpret(subTree, tempMap))
                Types.ASSIGNATION -> tempMap.putAll(AssignationInterpreter(ReadInputFromTerminal()).interpret(subTree, tempMap))
                Types.FUNCTION -> tempMap.putAll(PrintInterpreter(ReadInputFromTerminal()).interpret(subTree, tempMap))
                else -> throw IllegalArgumentException("Unsupported token type: ${subTree.getToken().getType()}")
            }
        }
        return tempMap.toMap()
    }
}
