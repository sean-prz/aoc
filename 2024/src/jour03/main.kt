package jour03

import helper.Helper.readInput


fun main() {
    println("Part 1 : ")
    Part1.main()
    println("\n ======= ")
    println("Part 2 : ")
    Part2.main()
}
object Part1 {
    fun main() {
        val input = readInput("03")
        val listOfMuls = findOperatorInString(input)
        val res = listOfMuls.map{ extractNumbers(it) }.map { it.reduce{acc, i -> acc * i} }.reduce { acc, i -> acc + i }
        print(res)
    }


    fun findOperatorInString(string: String) : Sequence<String> {
        val regex = Regex("mul\\(([0-9]|[1-9][0-9]|[1-9][0-9][0-9]),([0-9]|[1-9][0-9]|[1-9][0-9][0-9])\\)")
        return regex.findAll(string).map { it.value }
    }
    // takes a string and returns a list of two numbers.
    fun extractNumbers(string: String) : List<Int> {
        val regex = Regex("mul\\((\\d+),(\\d+)\\)")
        val match = regex.find(string) ?: return emptyList()
        return match.groupValues.drop(1).map{it.toInt()}
    }
}
object Part2 {
    fun main() {
        val input = readInput("03")
        val sanitizedInput = sanitizeInput(input)
        println(sanitizedInput)
        val inputWithoutMuls = removeMulsbetweendonts(sanitizedInput)
        println(inputWithoutMuls)
        val res = inputWithoutMuls.map{ Part1.extractNumbers(it) }.map { it.reduce{ acc, i -> acc * i} }.reduce { acc, i -> acc + i }
        println(res)
    }
}

// remove everything except the valid mul() do() and don't() and returns list of instructions
fun sanitizeInput(input: String) : List<String> {
    val regex = Regex("mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\)")
    return regex.findAll(input).map{it.value}.toList()
}


fun removeMulsbetweendonts(input : List<String>) : List<String> {
    var dont = false
    val result = mutableListOf<String>()
    for (instr in input) {
        if (instr == "do()") {
            dont = false
        }
        if (instr == "don't()") {
            dont = true
        }
        if (!dont && instr.contains("mul")) {
           result.add(instr)
        }
    }
    return result
}

