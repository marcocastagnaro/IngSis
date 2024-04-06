package org.example

class DeclarationASTfactory : ASTFactory {
    public override fun createAST(tokens: List<Token>): AbstractSyntaxTree {
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

    override fun canHandle(tokens: List<Token>): Boolean {
        if (tokens.any { it.getType() == Types.ASSIGNATION })
            {
                return false
            }
        if (tokens.any { it.getType() == Types.KEYWORD })
            {
                return true
            }
        return false
    }
}
