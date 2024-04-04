package org.example

class PrintNode(
    private var token: Token,
    private var child: AbstractSyntaxTree? = null,
) : AbstractSyntaxTree {
    override fun isLeaf(): Boolean {
        return false
    }
public fun setRight (child: AbstractSyntaxTree){
        this.child = child
}

    override fun getToken(): Token {
        return this.token
    }

    override fun getRight(): AbstractSyntaxTree? {
        return this.child
    }
}
