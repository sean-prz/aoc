package jour03

import java.io.File


fun main() {
    println("Part 1 : ")
    part1().part1()
    println("\n ======= ")
    println("Part 2 : ")
    part2().part2()
}
class part1 {
    fun part1() {
        val input = readInput()
        val listOfMuls = findOperatorInString(input)
        val res = listOfMuls.map{ extractNumbers(it) }.map { it.reduce{acc, i -> acc * i} }.reduce( {acc, i -> acc + i})
        print(res)
    }


    fun findOperatorInString(string: String) : Sequence<String> {
        val regex = Regex("mul\\(([0-9]|[1-9][0-9]|[1-9][0-9][0-9]),([0-9]|[1-9][0-9]|[1-9][0-9][0-9])\\)")
        return regex.findAll(string).map { it.value }
    }
    // takes a string and returns a list of two numbers.
    fun extractNumbers(string: String) : List<Int> {
        val regex = Regex("mul\\((\\d+),(\\d+)\\)")
        val match = regex.find(string)
        if (match == null) {
            return emptyList()
        }
        return match.groupValues.drop(1).map{it.toInt()}
    }
}
class part2 {
    fun part2() {
        val input = readInput()
        val sanitizedInput = sanitizeInput(input)
        println(sanitizedInput)
        val inputWithoutMuls = removeMulsbetweendonts(sanitizedInput)
        println(inputWithoutMuls)
        val res = inputWithoutMuls.map{ part1().extractNumbers(it) }.map { it.reduce{acc, i -> acc * i} }.reduce( {acc, i -> acc + i})
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


fun readInput() : String {
    val file = File( "./src/jour03/input03.txt")
    return file.readText()
}