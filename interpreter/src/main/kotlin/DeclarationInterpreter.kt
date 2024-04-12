package org.example

class DeclarationInterpreter : InterpreterStrategy {
    override fun interpret(
        tree: AbstractSyntaxTree,
        variables: HashMap<VariableToken, String?>,
    ): Map<VariableToken, String?> {
        val variable = getVariable(tree)
        val type = getType(tree)
        val mutableVariables = variables.toMutableMap()
        addVariable(variable!!, type, mutableVariables)
        return mutableVariables
    }

    private fun getVariable(tree: AbstractSyntaxTree): String? {
        return tree.getRight()?.getLeft()?.getToken()?.getValue()
    }

    private fun addVariable(
        variable: String,
        type: TokenType,
        variables: MutableMap<VariableToken, String?>,
        value: String? = null,
    ) {
        val processedValue =
            if (type == TokenType.STRING && value != null) {
                value.removeSurrounding("\"")
            } else {
                value
            }
        variables[VariableToken(variable, type)] = processedValue
    }

    private fun getType(tree: AbstractSyntaxTree): TokenType {
        val value = tree.getRight()?.getRight()?.getToken()?.getValue()
        return when (value) {
            "number" -> TokenType.NUMBER
            "string" -> TokenType.STRING
            else -> throw Exception("Error! Not Valid Type")
        }
    }
}
