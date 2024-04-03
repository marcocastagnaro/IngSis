import org.example.AbstractSyntaxTree
import org.example.PrintlnASTfactory
import org.example.Token
import org.example.Types

class Parser {
    fun execute(tokens: List<Token>): List<AbstractSyntaxTree> {
        val sameLineTokens = getSameLineTokens(tokens)
        val result = mutableListOf<AbstractSyntaxTree>()

        for (tokenList in sameLineTokens) {
            val astFactory = determineFactory(tokenList)
            if (astFactory != null) {
                result.add(astFactory.createAST(tokenList))
            } else {
                println("Error ...") // TODO : throw exception
            }
        }

        return result
    }

    private fun determineFactory(tokens: List<Token>): ASTFactory? {
        return when {
            hasPrintln(tokens) -> PrintlnASTfactory()
            hasAssignation(tokens) -> AssignationASTfactory()
            else -> null
        }
    }

    private fun hasPrintln(tokens: List<Token>): Boolean {
        return tokens.any { it.getType() == Types.FUNCTION && it.getValue() == "println" }
    }

    private fun hasAssignation(tokens: List<Token>): Boolean {
        return tokens.any { it.getValue() == "=" }
    }

    private fun getSameLineTokens(tokenList: List<Token>): List<List<Token>> {
        val rows = mutableListOf<List<Token>>()
        var singleRow = mutableListOf<Token>()
        for (token in tokenList) {
            if (token.getValue() != ";") {
                singleRow.add(token)
            } else {
                rows.add(singleRow)
                singleRow = mutableListOf()
            }
        }
        return rows
    }
}
