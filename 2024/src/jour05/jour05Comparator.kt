package jour05

import helper.Helper

fun main() {
    // start a timer
    Helper.startTimer()
    val input = Helper.readInput("05")
    Part1And2.main(input)
    Helper.stopTimer()
}


object Part1And2 {
    fun main(input : String) {
        var part1 = 0
        var part2 = 0

        // Parsing the input
        val (rules, messages) = input.split("\n\n")
        val rulesList     = findRules(rules)
        val messagesList  = parseMessages(messages)

        // Sorting the messages
        val sortedList = messagesList.map { it.sortedWith(makeComparator(rulesList)) }

        // Comparing the original and sorted messages, adding either to part1 or part2
        messagesList.zip(sortedList).forEach { (original, sorted) ->
            if (original == sorted)
                part1 += original[original.size / 2]
            else
                part2 += sorted[sorted.size / 2]
        }

        // Printing the results
        println(part1)
        println(part2)
    }


    private fun findRules(input : String): List<Pair<Int,Int>> {
        return  Regex("(\\d+)\\|(\\d+)")
            .findAll(input)
            .map {it.groupValues[1].toInt() to it.groupValues[2].toInt()}
            .toList()
    }
    private fun parseMessages(input : String) : List<List<Int>> {
        return input
            .lines()
            .filter { it.isNotBlank() }
            .map { line -> line.split(",").map { it.toInt() } }
    }
    private fun makeComparator(rules: List<Pair<Int,Int>>): Comparator<Int> {
        return Comparator { a, b ->
            when {
                rules.contains(a to b) -> -1
                rules.contains(b to a) -> 1
                else -> 0
            }
        }
    }
}
