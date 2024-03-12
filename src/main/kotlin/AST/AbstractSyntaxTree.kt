package org.example.AST
import org.example.Token.Token

class AbstractSyntaxTree(val value: Token, var left: AbstractSyntaxTree? = null, var right: AbstractSyntaxTree? = null) {

    fun getLeft(): AbstractSyntaxTree? {
        return left
    }

    fun getRight(): AbstractSyntaxTree? {
        return right
    }

    fun isLeaf(): Boolean {
        return left == null && right == null
    }

    fun getValue(): String {
        return value.getValue()
    }
}
