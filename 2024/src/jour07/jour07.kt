package jour07

import helper.Helper
import kotlin.math.pow

val testInput =
    """
190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20
    """.trimIndent()


fun main() {
    Helper.startTimer()
    val input = Helper.readInput("07")
    Part1.main(input)
    Part2.main(input)
    Helper.stopTimer()
}

object Part1 {
    fun main(input: String) {
        val inputList = input.lines().map { it ->
            it.split(" ").map{ Regex("(\\d+)").find(it)?.value!!.toLong() }
        }
        var result = 0L
        inputList.forEach {
            val goal = it.first()
            val operands = it.drop(1)
            val numberOfOperatorsSpot = operands.size - 1
            val numberOfCombination = 2.0.pow((numberOfOperatorsSpot))
            println("Goal : $goal, Operands : $operands, Number of combination : $numberOfCombination")

            for (i in 0..< numberOfCombination.toInt()) {
                val ope =  ArrayDeque(operands)
                var current = ope.removeFirst()
                for (j in 0..< numberOfOperatorsSpot) {
                    val binary = i shr j and 1
                    when (binary) {
                        0 -> current += ope.removeFirst()
                        1 -> current *= ope.removeFirst()
                    }
                    if (current > goal) {
                        break
                    }
                }
                if (current == goal) {
                    result += goal
                    break
                }
            }

        }

    println("Part 1 Result : $result")
    }
}

object Part2 {
    fun main(input: String) {
        val inputList = input.lines().map { it ->
            it.split(" ").map{ Regex("(\\d+)").find(it)?.value!!.toLong() }
        }
        var result = 0L
        inputList.forEach {
            val goal = it.first()
            val operands = it.drop(1)
            val numberOfOperatorsSpot = operands.size - 1
            val numberOfCombination = 3.0.pow((numberOfOperatorsSpot))

            for (i in 0..< numberOfCombination.toInt()) {
                val binaryInString = i.toString(3).padStart(numberOfOperatorsSpot, '0')
                val ope =  ArrayDeque(operands)
                var current = ope.removeFirst()
                for (j in 0..< numberOfOperatorsSpot) {
                    val terString = binaryInString[j]
                    when (terString) {
                        '0' -> current += ope.removeFirst()
                        '1' -> current *= ope.removeFirst()
                        '2' -> {
                            current = (current.toString() +  ope.removeFirst().toString()).toLong()
                        }
                        else -> throw Exception("Invalid ternary digit")
                    }
                    if (current > goal) {
                        break
                    }
                }
                if (current == goal) {
                    result += goal
                    break
                }
            }
        }
        println("Part 2 : $result")
    }
}