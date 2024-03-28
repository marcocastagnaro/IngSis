interface AbstractSyntaxTree {
    fun getLeft(): AbstractSyntaxTree? = null

    fun getRight(): AbstractSyntaxTree? = null

    fun isLeaf(): Boolean

    fun getToken(): Token
}
