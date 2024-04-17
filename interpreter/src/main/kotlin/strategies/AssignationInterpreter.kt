package org.example.strategies

import org.example.AbstractSyntaxTree
import org.example.InterpreterStrategy
import org.example.Output
import org.example.Types
import org.example.inputReader.InputReaderType
import org.example.strategies.strategyHelpers.OperationInterpreter
import org.example.strategies.strategyHelpers.ReadEnvInterpreter
import org.example.strategies.strategyHelpers.ReadInputInterpreter

class AssignationInterpreter(
    private val inputReader: InputReaderType,
    private val output: Output,
) : InterpreterStrategy {
    override fun interpret(
        tree: AbstractSyntaxTree,
        variables: HashMap<VariableToken, String?>,
        inmutableList: MutableList<String>,
    ): Map<VariableToken, String?> {
        val tempMap = variables.toMutableMap()
        if (isAssignation(tree)) {
            val variable = getVariableFromAssignation(tree)
            checkVAriableIsNotConst(variable, inmutableList)
            val value = getTokenValue(tree.getRight()!!, variables)
            val actualValue = getActualValue(tempMap, variable)
            if (actualValue != null) {
                updateValue(tempMap, variable, actualValue, value)
            } else {
                throw IllegalArgumentException("Variable $variable not declared")
            }
        } else {
            addValuesToMap(tree, tempMap, inmutableList)
        }
        return tempMap
    }

    private fun updateValue(
        tempMap: MutableMap<VariableToken, String?>,
        variable: String?,
        actualValue: MutableMap.MutableEntry<VariableToken, String?>,
        value: String?,
    ) {
        tempMap[VariableToken(variable!!, actualValue.key.type)] = value
    }

    private fun getActualValue(
        tempMap: MutableMap<VariableToken, String?>,
        variable: String?,
    ) = tempMap.entries.firstOrNull { it.key.value == variable }

    private fun checkVAriableIsNotConst(
        variable: String?,
        inmutableList: MutableList<String>,
    ) {
        if (variable in inmutableList) {
            throw IllegalArgumentException("Variable $variable es inmutable")
        }
    }

    private fun isAssignation(tree: AbstractSyntaxTree) = tree.getLeft()?.getToken()?.getType() == Types.IDENTIFIER

    private fun addValuesToMap(
        tree: AbstractSyntaxTree,
        variables: MutableMap<VariableToken, String?>,
        inmutableList: MutableList<String>,
    ) {
        val keyword = getVariableFromAssignation(tree)
        if (keyword == "const") {
            addToInmutableList(tree, inmutableList)
        }
        val variable = getVariable(tree)
        val type = getType(tree)
        val value = getTokenValue(tree.getRight()!!, variables)
        addVariable(variable!!, type, value!!, variables)
    }

    private fun addToInmutableList(
        tree: AbstractSyntaxTree,
        inmutableList: MutableList<String>,
    ) {
        tree.getLeft()?.getRight()?.getLeft()?.getToken()?.getValue()?.let { inmutableList.add(it) }
    }

    private fun getVariableFromAssignation(tree: AbstractSyntaxTree) = tree.getLeft()?.getToken()?.getValue()

    private fun getVariable(tree: AbstractSyntaxTree): String? {
        return tree.getLeft()?.getRight()?.getLeft()?.getToken()?.getValue()
    }

    private fun getType(tree: AbstractSyntaxTree): TokenType {
        val value = tree.getLeft()?.getRight()?.getRight()?.getToken()?.getValue()
        return when (value) {
            "number" -> TokenType.NUMBER
            "string" -> TokenType.STRING
            "boolean" -> TokenType.BOOLEAN
            else -> throw Exception("Error! Not Valid Type")
        }
    }

    private fun getTokenValue(
        tree: AbstractSyntaxTree,
        variables: MutableMap<VariableToken, String?>,
    ): String? {
        val token = tree.getToken()
        return when (token.getType()) {
            Types.READENV -> ReadEnvInterpreter().readEnvVariables(tree.getRight()!!)
            Types.LITERAL, Types.BOOLEAN -> token.getValue()
            Types.FUNCTION -> ReadInputInterpreter(inputReader).getInput(tree, Types.FUNCTION, output)
            Types.OPERATOR -> {
                val leftValue = getTokenValue(tree.getLeft()!!, variables)
                val rightValue = getTokenValue(tree.getRight()!!, variables)
                OperationInterpreter().performOperation(token.getValue(), leftValue!!, rightValue!!, variables).toString()
            }

            else -> null
        }
    }

    private fun addVariable(
        variable: String,
        type: TokenType,
        value: String,
        variables: MutableMap<VariableToken, String?>,
    ) {
        val processedValue =
            if (type == TokenType.STRING) {
                value.removeSurrounding("\"")
            } else {
                value
            }
        variables[VariableToken(variable, type)] = processedValue
    }
}
