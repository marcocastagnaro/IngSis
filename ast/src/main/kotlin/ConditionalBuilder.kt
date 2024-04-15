package org.example

class ConditionalBuilder {
    private var token: Token? = null
    private var right: AbstractSyntaxTree? = null
    private var left: AbstractSyntaxTree? = null
    private var body: List<AbstractSyntaxTree>? = null

    fun setToken(token: Token): ConditionalBuilder {
        this.token = token
        return this
    }

    fun setRight(right: AbstractSyntaxTree): ConditionalBuilder {
        this.right = right
        return this
    }

    fun setLeft(left: AbstractSyntaxTree): ConditionalBuilder {
        this.left = left
        return this
    }

    fun setBody(body: List<AbstractSyntaxTree>): ConditionalBuilder {
        this.body = body
        return this
    }

    fun build(): AbstractSyntaxTree {
        return ConditionalNode(
            token = token!!,
            right = right,
            left = left!!,
            body = body,
        )
    }
}
