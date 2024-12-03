package jour02

import java.io.File



fun main() {
    val file = File( "./src/jour02/input02.txt")
    val fileString = file.readText()
    val lines = fileString.lines().filterNot { it.isEmpty() }
    val reports = lines.map {
        it.split(" ").filterNot { it.isEmpty() }.map{it.toInt()}
        }
    val result = reports.map { analyzeReport2(it) }.filter{it}.size

    reports.forEach {
        if(!analyzeReport2(it)) {
            print(it)
            print(" ")
            println(analyzeReport2(it))
        }

    }
    println(result)
    println(" --- test ---")
    val test01 = listOf(31, 31, 29, 28)
    print(test01)
    println(" : " + analyzeReport(test01))

    }

fun analyzeReport(report : List<Int>): Boolean {
    var increasing : Boolean? = null

    for (i in 1 until report.size) {
        val diff = report[i] - report[i-1]
        if (diff > 3 || diff < -3 || diff == 0) {
            return false
        }
        if (increasing == null) {
            increasing = diff > 0
        }
        if (increasing != diff > 0) {
            return false
        }
    }
    return true
}

fun analyzeReport2(report : List<Int>): Boolean {
    var increasing : Boolean? = null

    for (i in 1 until report.size) {
        val diff = report[i] - report[i-1]
        if (diff > 3 || diff < -3 || diff == 0) {
            return analyzeReportWithSublist(report.toMutableList())
        }
        if (increasing == null) {
            increasing = diff > 0
        }
        if (increasing != diff > 0) {
            return analyzeReportWithSublist(report.toMutableList())
        }
    }
    return true
}

fun analyzeReportWithSublist(report : MutableList<Int>): Boolean {
    for (i in 0..< report.size) {
         val copy = report.toMutableList()
         copy.removeAt(i)
        if (analyzeReport(copy)) {
            return true
        }
    }
    return false
}


