package com.vladiknt.calcnt

import java.lang.Exception
import kotlin.math.pow

object Calculate {

    fun result(input: String): String {
        var result = ""
        try {
            result = core(Pair("0", input)).second

            if (result == "NaN")
                result = "error"
            // Rounding to int if result is something like 4.0
            else if ((result.toDouble() * 100000000).toLong() == result.toDouble().toLong() * 100000000)
                result = (result.toDouble().toInt()).toString()
            // To avoid situations like "0.1 + 0.2 = 0.3000000000004"
            else if ((result.toDouble() * 100000000).toLong() / 100000000.0 != result.toDouble())
                result = ((result.toDouble() * 100000000).toLong() / 100000000.0).toString()

            if (result == "9.223372036854776E10")
                result = "very much"
        } catch (math: Exception) {
            return "error"
        }

        return result
    }

    private fun core(input: Pair<String, String> /*first - i, second - expression*/): Pair<String, String> /* first - i, second - result*/ {
        val list = ArrayList<String>()
        var number = "" // if we have a part of number, we`ll add it there and get a full number after all
        var i = input.first.toInt()
        val str = input.second
        while (i < str.length) {
            if (str[i] == ')') {
                i++
                break
            }
            if (str[i] != '(') {
                when (str[i]) {
                    '+', '-', '×', '÷', '^' -> {
                        list.add(str[i].toString())
                    }
                    else -> {
                        try {
                            if (str[i + 1] != '.')
                                Integer.parseInt(str[i + 1].toString())
                            // if next char is digit
                            number += str[i]
                        } catch (e: Exception) {
                            // if next char isn`t digit
                            number += str[i]
                            list.add(number)
                            number = ""
                        }
                    }
                }
                i++
            } else {
                val output = core(Pair((i + 1).toString(), str))
                i = output.first.toInt()
                list.add(output.second)
            }
        }

        // calculating from list
        var iterator = 0
        var a: Double
        var b: Double
        // ^ firstly
        while (true) {
            if (iterator == list.size) {
                iterator = 0
                break
            }
            if (list[iterator] == "^") {
                b = (list.removeAt(iterator + 1)).toDouble()
                list.removeAt(iterator)
                a = (list.removeAt(iterator - 1)).toDouble()
                list.add(iterator - 1, (a.pow(b)).toString())
                iterator = 0
            }
            iterator++;
        }
        // × and ÷
        while (true) {
            if (iterator == list.size) {
                iterator = 0
                break
            }
            if (list[iterator] == "×") {
                b = (list.removeAt(iterator + 1)).toDouble()
                list.removeAt(iterator)
                a = (list.removeAt(iterator - 1)).toDouble()
                list.add(iterator - 1, (a * b).toString())
                iterator = 0
            }
            if (list[iterator] == "÷") {
                b = (list.removeAt(iterator + 1)).toDouble()
                list.removeAt(iterator)
                a = (list.removeAt(iterator - 1)).toDouble()
                list.add(iterator - 1, (a / b).toString())
                iterator = 0
            }
            iterator++;
        }
        // + and -
        while (true) {
            if (iterator == list.size) {
                iterator = 0
                break
            }
            if (list[iterator] == "+") {
                b = (list.removeAt(iterator + 1)).toDouble()
                list.removeAt(iterator)
                a = (list.removeAt(iterator - 1)).toDouble()
                list.add(iterator - 1, (a + b).toString())
                iterator = 0
            }
            if (list[iterator] == "-") {
                b = (list.removeAt(iterator + 1)).toDouble()
                list.removeAt(iterator)
                a = (list.removeAt(iterator - 1)).toDouble()
                list.add(iterator - 1, (a - b).toString())
                iterator = 0
            }
            iterator++;
        }

        return Pair(i.toString(), list.removeAt(0))
    }

}