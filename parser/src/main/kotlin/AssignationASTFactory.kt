package org.example

class AssignationASTFactory : ASTFactory {
    override fun createAST(tokens: List<Token>): AbstractSyntaxTree {
        val root = NodeBuilder()
        root.setValue(tokens.find { it.getType() == Types.ASSIGNATION }!!)
        val leftTokens = tokens.takeWhile { it.getValue() != "=" }
        root.setLeft(NodeBuilder().setValue(leftTokens.first()).build()) // Agarro el primero ya que va a ser un unico valor
        val rightTokens = tokens.drop(leftTokens.size + 1)
        if (rightTokens.size > 1) {
            val right = operationsDeclarator(rightTokens)
            root.setRight(right)
        } else {
            root.setRight(NodeBuilder().setValue(rightTokens[0]).build())
        }
        return root.build()
    }

    private fun operationsDeclarator(tokens: List<Token>): AbstractSyntaxTree {
        val nodes = tokens.stream().map { NodeBuilder().setValue(it) }.toList().toMutableList()
        var j = 0
        while (j < nodes.size) {
            if (nodes[j].getValue()!!.getValue() == "/" || nodes[j].getValue()!!.getValue() == "*") {
                nodes[j].setLeft(nodes[j - 1].build())
                nodes[j].setRight(nodes[j + 1].build())
                nodes.removeAt(j - 1)
                nodes.removeAt(j)
            }
            j++
        }
        var i = 0
        while (i < nodes.size) {
            if (nodes[i].getValue()!!.getValue() == "+" || nodes[i].getValue()!!.getValue() == "-") {
                nodes[i].setLeft(nodes[i - 1].build())
                nodes[i].setRight(nodes[i + 1].build())
                nodes.removeAt(i - 1)
                nodes.removeAt(i)
            }
            i++
        }
        return nodes[0].build()
    }

    override fun canHandle(tokens: List<Token>): Boolean {
        if (tokens.any { it.getType() == Types.ASSIGNATION } && tokens.any { it.getType() != Types.KEYWORD })
            {
                return true
            }
        return false
    }
}
