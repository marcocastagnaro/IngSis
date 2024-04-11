 package org.example.factory

 import org.example.*

 class ConditionalFactory : ASTFactory {
    override fun createAST(tokens: List<Token>): AbstractSyntaxTree {
        val parser = Parser()
        val root = NodeBuilder()
        root.setValue(tokens.find { it.getType() == Types.CONDITIONAL }!!)
        val indexFirstParenthesis = tokens.indexOfFirst { it.getType() == Types.PARENTHESIS }
        val condition = tokens.subList(indexFirstParenthesis + 1, tokens.indexOfFirst { it.getType() == Types.PARENTHESIS })
        root.setLeft(NodeBuilder().setValue(condition[0]).build())
        val indexFirstCurlyBrace = tokens.indexOfFirst { it.getValue() == "{" }
        val body = tokens.subList(indexFirstCurlyBrace + 1, tokens.indexOfLast { it.getValue() == "}" })
        val trees = parser.execute(body)
        addListOfTrees(trees, root)
        return root.build()
    }
     private fun addListOfTrees(trees: List<AbstractSyntaxTree>, root: NodeBuilder) {
         var currentNode = root
         for (tree in trees) {
             currentNode.setRight(tree)
             currentNode = currentNode.getRight() as NodeBuilder
         }
     }

     override fun canHandle(tokens: List<Token>): Boolean {
        if (tokens.find{ it.getType() == Types.CONDITIONAL } != null) {
            return true
        }
        return false;
    }
 }

 //if (true) {
 //    println("hola")
 //} else {
 //    println("adios")
 //}
