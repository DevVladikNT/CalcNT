package com.vladiknt.calcnt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var isResult = true
    private var expression = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun clear(view: View?) {
        if (isResult)
            expression = ""
        else {
            when (view?.id) {
                R.id.allClear -> expression = ""
                R.id.singleClear -> expression =
                    expression.subSequence(0, expression.length - 1) as String
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

}