package org.example

class ReadInputInterpreter : InterpreterStrategy {
    override fun interpret(
        tree: AbstractSyntaxTree,
        variables: Map<VariableToken, String>,
    ): Map<VariableToken, String> {
        val tokens = ParseTreeToTokens().parseToTokens(tree)
        val symbol = tokens.find { it.getType() == Types.IDENTIFIER }
        val type = tokens.find { it.getType() == Types.DATA_TYPE }
        TODO("Not yet implemented")
    }
}
