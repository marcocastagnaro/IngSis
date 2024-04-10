package org.example.validator.InterpreterStrategy

import org.example.AbstractSyntaxTree
import org.example.TokenType
import org.example.VariableToken

class AssignationInterpreter : InterpreterStrategy {

    public override fun interpret(
        tree: AbstractSyntaxTree,
        variables: Map<VariableToken, String>
    ): Map<VariableToken, String> {
        val variable = getVariable(tree)
        val type = getType(tree)
        val value = getValue(tree.getRight()!!)

        if (variable == null || value == null) {
            throw Exception("Error! Not Valid Assignation")
        } else {
            return addValuesToMap(tree, variables.toMutableMap())
        }
    }

    private fun getVariable(tree: AbstractSyntaxTree): String? {
        return tree.getLeft()?.getRight()?.getLeft()?.getToken()?.getValue()
    }

    private fun getType(tree: AbstractSyntaxTree): TokenType {
        val value = tree.getLeft()?.getRight()?.getRight()?.getToken()?.getValue()
        return when (value) {
            "number" -> TokenType.NUMBER
            "string" -> TokenType.STRING
            else -> throw Exception("Error! Not Valid Type")
        }
    }

    private fun addValuesToMap(
        tree: AbstractSyntaxTree,
        variables: MutableMap<VariableToken, String>
    ): Map<VariableToken, String> {

    }


    private fun getValue(tree: AbstractSyntaxTree): String? {
        if (tree.isLeaf()) return tree.getToken().getValue()
        getValue(tree.getRight()!!)
        getValue(tree.getLeft()!!)
    }

    private fun addVariable(
        variable: String,
        type: TokenType,
        value: String,
        variables: Map<VariableToken, String>
    ): Map<VariableToken, String> {
        val variableToken = VariableToken(variable, type)
        return variables + (variableToken to value)
    }


}