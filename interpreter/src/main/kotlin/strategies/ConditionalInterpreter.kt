package org.example.strategies

import org.example.AbstractSyntaxTree
import org.example.ConditionalLeaf
import org.example.InterpreterStrategy
import org.example.Output
import org.example.Types
import org.example.inputReader.InputReaderType

class ConditionalInterpreter(
    private val inputReader: InputReaderType,
    private val output: Output,
) : InterpreterStrategy {
    override fun interpret(
        tree: AbstractSyntaxTree,
        variables: HashMap<VariableToken, String?>,
        inmutableList : MutableList<String>
    ): Map<VariableToken, String?> {
        if (isConditionTrue(tree, variables)) {
            val body = tree.getLeft()!!.getRight() as ConditionalLeaf
            return solveBody(body, variables, inmutableList)
        } else {
            if (tree.getRight() == null) {
                return variables
            }
            val body = tree.getRight()!!.getRight() as ConditionalLeaf
            return solveBody(body, variables, inmutableList)
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
        inmutableList: MutableList<String>
    ): Map<VariableToken, String?> {
        val tempMap = HashMap(variables)
        for (subTree in tree.getBody()) {
            when (subTree.getToken().getType()) {
                Types.KEYWORD -> tempMap.putAll(DeclarationInterpreter().interpret(subTree, tempMap, inmutableList))
                Types.ASSIGNATION -> tempMap.putAll(AssignationInterpreter(inputReader, output).interpret(subTree, tempMap, inmutableList))
                Types.FUNCTION -> tempMap.putAll(PrintInterpreter(inputReader, output).interpret(subTree, tempMap, inmutableList))
                else -> throw IllegalArgumentException("Unsupported token type: ${subTree.getToken().getType()}")
            }
        }
        return tempMap.toMap()
    }
}
