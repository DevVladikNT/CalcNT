package com.vladiknt.calcnt

import org.junit.Test

import org.junit.Assert.*

class CalculateUnitTest {

    @Test
    fun simpleOperations() {
        val expr = "2+2÷2-10×(30-10)"
        assertEquals("-197", Calculate.result(expr))
    }

    @Test
    fun singleNumber() {
        val expr = "0"
        assertEquals("0", Calculate.result(expr))
    }

    @Test
    fun mathPow() {
        val expr = "9^(0.5)"
        assertEquals("3", Calculate.result(expr))
    }

    @Test
    fun doubleValue() {
        val expr = "0.2×7"
        assertEquals("1.4", Calculate.result(expr))
    }

    @Test
    fun incorrectExpr() {
        val expr = "+"
        assertEquals("error", Calculate.result(expr))
    }

    @Test
    fun veryBigValue() {
        val expr = "100000000000000000000000×10000000000000000000000"
        assertEquals("∞", Calculate.result(expr))
    }

    @Test
    fun verySmallValue() {
        val expr = "5÷(-0)"
        assertEquals("-∞", Calculate.result(expr))
    }

    @Test
    fun divByZero() {
        val expr = "0÷0"
        assertEquals("error", Calculate.result(expr))
    }

    @Test
    fun negativeNumber() {
        val expr = "-3+5"
        assertEquals("2", Calculate.result(expr))
    }

    @Test
    fun positiveNumber() {
        val expr = "+5-3"
        assertEquals("2", Calculate.result(expr))
    }

    @Test
    fun zeroPointNineError() {
        val expr = "0.3×3"
        assertEquals("0.9", Calculate.result(expr))
    }

}