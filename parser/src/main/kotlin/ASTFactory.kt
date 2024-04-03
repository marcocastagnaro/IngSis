import org.example.AbstractSyntaxTree
import org.example.Token

interface ASTFactory {
    fun createAST(tokens: List<Token>): AbstractSyntaxTree
}
