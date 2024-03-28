class Output {
    var string = ""

    fun buildOutput(newString: String) {
        string = string + newString
    }

    fun print() {
        println(string)
    }
}
