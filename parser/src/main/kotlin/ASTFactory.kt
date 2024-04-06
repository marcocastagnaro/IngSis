import org.example.AbstractSyntaxTree
import org.example.Token

interface ASTFactory {
    fun createAST(tokens: List<Token>): AbstractSyntaxTree

    fun canHandle(tokens: List<Token>): Boolean
}
