package jour05

import helper.Helper
import jour05.Part1.findRules
import jour05.Part1.parseMessages

fun main() {
    val input = Helper.readInput("05")
    println(Part1.main(input))
    println(Part2.main(input))
}


object  Part1 {
    fun main(input: String) : Int {
        val (rules, messages) = input.split("\n\n")
        val rulesList = findRules(rules)
        val messagesList = parseMessages(messages)
        return messagesList
            .filter { verifyOneMessage(it, rulesList) }
            .sumOf { it[it.size/2] }
    }

    fun findRules(input : String): List<Pair<Int,Int>> {
        return  Regex("(\\d+)\\|(\\d+)")
            .findAll(input)
            .map {it.groupValues[1].toInt() to it.groupValues[2].toInt()}
            .toList()
    }
    fun parseMessages(input : String) : List<List<Int>> {
        return input
            .lines()
            .filter { it.isNotBlank() }
            .map { line -> line.split(",").map { it.toInt() } }
    }

    fun verifyOneMessage(message: List<Int>, rules: List<Pair<Int,Int>>): Boolean {
        val results : List<Boolean> = rules.map { (before, after) ->
            val indexBefore = message.indexOfFirst{ it == before }
            val indexAfter = message.indexOfFirst{ it == after }
            indexBefore <= indexAfter || indexBefore == -1 || indexAfter == -1
        }
        return results.all { it }
    }
}

object Part2 {
    fun main(input:String): Int {
        val (rules, messages) = input.split("\n\n")
        val rulesList = findRules(rules)
        val messagesList = parseMessages(messages)
        val messagesNotOrdered = messagesList.filterNot { Part1.verifyOneMessage(it, rulesList) }
        val fixedMessage = messagesNotOrdered.map { fixBreakingRule(it, rulesList) }
        return fixedMessage.sumOf { it[it.size/2] }
    }

    fun findBreakingRule(message: List<Int>, rules: List<Pair<Int, Int>>): Pair<Int, Int> {
        rules.forEach { (before, after) ->
            val indexBefore = message.indexOfFirst{ it == before }
            val indexAfter = message.indexOfFirst{ it == after }
            if (indexBefore == -1 || indexAfter == -1) {
                return@forEach
            }
            if (indexBefore > indexAfter) {
               return indexBefore to indexAfter
            }
        }
       throw Exception("No breaking rule found")
    }

    fun swapMessage(message: List<Int>, indexMin: Int, indexMax: Int): List<Int> {
        val messageCopy = message.toMutableList()
        val temp = messageCopy[indexMin]
        messageCopy[indexMin] = messageCopy[indexMax]
        messageCopy[indexMax] = temp
        return messageCopy
    }
    fun fixBreakingRule(message: List<Int>, rules: List<Pair<Int, Int>>): List<Int> {
        var fixedMessage = message
        while(!Part1.verifyOneMessage(fixedMessage, rules)) {
            val breakingRule = findBreakingRule(fixedMessage, rules)
            fixedMessage = swapMessage(fixedMessage, breakingRule.first, breakingRule.second)
        }
        return fixedMessage
    }
}