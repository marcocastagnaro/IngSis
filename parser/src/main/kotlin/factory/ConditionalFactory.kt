 package org.example

 import org.example.*


 class ConditionalFactory : ASTFactory {
    override fun createAST(tokens: List<Token>): AbstractSyntaxTree {
        val parser = Parser()
        val indexFirstParenthesis = tokens.indexOfFirst { it.getType() == Types.PARENTHESIS }

        val condition = tokens[indexFirstParenthesis + 1]
        val indexFirstCurlyBrace = tokens.indexOfFirst { it.getValue() == "{" }
        val indexEndIf = tokens.indexOfFirst { it.getType() == Types.PUNCTUATOR && it.getValue() == "}"}
        val findElse = tokens[indexEndIf + 1]


        if (indexFirstParenthesis != -1 && indexFirstCurlyBrace != -1) {
            val ifTokens = tokens.slice(indexFirstCurlyBrace + 1 until indexEndIf)
            var elseTokens : List < Token> = emptyList()
            if (findElse.getType() == Types.CONDITIONAL && findElse.getValue() == "else") {
                val afterElse = tokens.slice(indexEndIf + 1 until tokens.size)
                val findEnd = afterElse.indexOfFirst { it.getType() == Types.PUNCTUATOR && it.getValue() == "}" }
                 elseTokens = tokens.slice(indexEndIf + 2 until findEnd)
            }

            val ifTrees = parser.execute(ifTokens)
            val elseTrees = if (elseTokens.isNotEmpty()) parser.execute(elseTokens) else emptyList()
            val root = ConditionalNode(condition, getTreesFromList(ifTrees), getTreesFromList(elseTrees))
            return root

        }
        throw Exception("Error")
    }
     private fun getTreesFromList (trees : List<AbstractSyntaxTree>) : AbstractSyntaxTree {

         if (trees.size == 1) {
             return trees[0]
         }
         val root = CompositeAbstractSyntaxTree(trees[0].getToken())
         root.setLeft(trees[0])
         root.setRight(getTreesFromList(trees.slice(1 until trees.size)))
         return root
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
