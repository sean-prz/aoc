package jour01

import java.io.File
import java.io.FileReader
import java.io.IOException
import kotlin.math.max

fun part1() {
    println("Hello World!")
    val list1 : MutableList<Int> = mutableListOf()
    val list2 : MutableList<Int> = mutableListOf()
    var isList1 = true
    // load files
    val file = File("./src/d01/", "input01.txt")
    val fileString = file.readText()
    fileString.lines().forEach {
        it.split(" ").filterNot { it.isEmpty() }.forEach {
            if (isList1) {
                list1.add(it.toInt())
            } else {
                list2.add(it.toInt())
            }
            isList1 = !isList1
        }
    }
    list1.sort()
    list2.sort()
    var sum = 0
    list1.forEachIndexed {
        index, el ->
        run {
            var el2 = list2[index]
            val dif = max(el2 - el, el - el2)
            sum += dif
        }
    }
    println("Sum: $sum")
}
fun main() {
    println("Hello World!")
    val list1 : MutableList<Int> = mutableListOf()
    val list2 : MutableList<Int> = mutableListOf()
    var isList1 = true
    // load files
    val file = File("./src/jour01/", "input01.txt")
    val fileString = file.readText()
    fileString.lines().forEach {
        it.split(" ").filterNot { it.isEmpty() }.forEach {
            if (isList1) {
                list1.add(it.toInt())
            } else {
                list2.add(it.toInt())
            }
            isList1 = !isList1
        }
    }
    var similiaryScore = 0
    list1.sort()
    list2.sort()
    list1.forEach { el1 ->
        similiaryScore += list2.count { el2 ->
            el1 == el2
        } * el1
    }
    print(similiaryScore)
}
