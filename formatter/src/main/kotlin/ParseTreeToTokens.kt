import org.example.AbstractSyntaxTree
import org.example.Token.Token

class ParseTreeToTokens {
    fun parseToString(root: AbstractSyntaxTree): List<Token> {
        val tokenList: MutableList<Token> = mutableListOf()
        getTokens(root, tokenList)
        return tokenList
    }

    private fun getTokens(
        root: AbstractSyntaxTree,
        tokenList: MutableList<Token>,
    ) {
        if (root.getLeft() != null) {
            getTokens(root.getLeft()!!, tokenList)
        }
        tokenList.add(root.getToken())
        if (root.getRight() != null) {
            getTokens(root.getRight()!!, tokenList)
        }
    }
}
