package org.example

class ReadInputInterpreter {
    fun getInput(
        ast: AbstractSyntaxTree,
        statementType: Types,
    ): String {
        if (statementType == Types.FUNCTION) {
            return readInput()
        } else if (statementType == Types.ASSIGNATION) {
            return checkInput(ast)
        } else {
            throw Exception("Error! Not Valid Type")
        }
    }

    private fun checkInput(ast: AbstractSyntaxTree): String {
        val input = readInput()
        val tokens = ParseTreeToTokens().parseToTokens(ast)
        val variableType = tokens.filter { it.getType() == Types.DATA_TYPE }.first().getValue()
        if (variableType == "number") {
            if (!input.matches(Regex("[0-9]+"))) {
                throw Exception("Error! Not Valid Type")
            }
        } else if (variableType == "string") {
            if (!input.matches(Regex("(\\d+|\"[^\"]*\"|'[^']*')"))) {
                throw Exception("Error! Not Valid Type")
            }
        }
        return input
    }

    private fun readInput(): String {
        // TODO("Quitos tenes que implementarlo vos con el cli")
        return readLine() ?: "Falta agregar el readInput en el CLI"
    }
}
