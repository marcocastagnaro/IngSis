package AST

import org.example.AbstractSyntaxTree
import org.example.NodeBuilder
import org.example.Token.Position
import org.example.Token.Token
import org.example.Token.Types

class AstBuilder {
    fun buildTree(lines: List<String>, index: Int = 0): AbstractSyntaxTree? {
        if (index >= lines.size) return null
        val line = lines[index]
        val parts = line.split(",")

        // Parse values from the line
        val value = Token(Types.DATA_TYPE, parts[0], Position(0,0), Position(0,1))


        val builder = NodeBuilder()
        builder.setValue(value)
        buildTree(lines, index + 1)?.let { builder.setLeft(it) }
        buildTree(lines, index + 2)?.let { builder.setRight(it) }
        return builder.build()
    }

    // Function to print the AST structure (modify for your desired output)
    fun printAST(ast: AbstractSyntaxTree?, level: Int = 0) {
        if (ast == null) return
        println(" ".repeat(level * 2) + ast.getToken())
        printAST(ast.getLeft(), level + 1)
        printAST(ast.getRight(), level + 1)
    }

}