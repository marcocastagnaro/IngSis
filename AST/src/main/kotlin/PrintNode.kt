class PrintNode(
    private var token: Token,
    private var right: AbstractSyntaxTree? = null,
) : AbstractSyntaxTree {
    override fun isLeaf(): Boolean {
        return false
    }

    override fun getToken(): Token {
        return this.token
    }

    override fun getRight(): AbstractSyntaxTree? {
        return this.right
    }
}
