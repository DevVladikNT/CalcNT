package com.vladiknt.calcnt

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlin.math.exp

class MainActivity : AppCompatActivity() {

    private var isResult = true
    private var expression = ""
    private var memory = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
    }

    fun clear(view: View?) {
        if (isResult)
            expression = ""
        else {
            when (view?.id) {
                R.id.allClear -> expression = ""
                R.id.singleClear -> if (expression.isNotEmpty()) expression = expression.subSequence(0, expression.length - 1) as String
            }
        }
        findViewById<TextView>(R.id.result).text = expression
    }

    fun result(view: View?) {
        isResult = true
        expression = Calculate.result(expression)
        findViewById<TextView>(R.id.result).text = expression
    }

    fun addSymbol(view: View?) {
        val newView = view as TextView
        if (isResult) {
            when (newView.text) {
                "+", "-", "×", "÷", "^" -> {}
                else -> expression = ""
            }
            isResult = false
        }
        expression += newView.text
        findViewById<TextView>(R.id.result).text = expression
    }

    fun saveValue(view: View?) {
        memory = expression
    }

    fun pasteValue(view: View?) {
        expression += memory
        findViewById<TextView>(R.id.result).text = expression
    }

}