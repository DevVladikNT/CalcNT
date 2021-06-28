package com.vladiknt.calcnt

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.core.view.marginBottom
import kotlin.math.exp

class MainActivity : AppCompatActivity() {

    private var isResult = true
    private var expression = ""
    private var memory = ""

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
                R.id.singleClear -> if (expression.isNotEmpty()) expression = expression.subSequence(0, expression.length - 1) as String
            }
        }
        findViewById<TextView>(R.id.result).text = expression
    }

    fun result(view: View?) {
        if(expression == "19022001+") {
            // Easter egg
            val picture = when ((Math.random() * 10000).toInt() % 4) {
                0 -> R.raw.ahegao1
                1 -> R.raw.ahegao2
                2 -> R.raw.ahegao3
                else -> R.raw.ahegao4
            }
            findViewById<TextView>(R.id.result).let{
                it.text = resources.openRawResource(picture).readBytes().decodeToString()
                it.textSize = 14F
            }
            findViewById<LinearLayout>(R.id.keyboard).visibility = View.GONE
        } else {
            // Proper work
            isResult = true
            expression = Calculate.result(expression)
            findViewById<TextView>(R.id.result).text = expression
        }
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
        findViewById<TextView>(R.id.memory).text = memory
    }

    fun pasteValue(view: View?) {
        expression += memory
        findViewById<TextView>(R.id.result).text = expression
    }

    fun menuButton(view: View?) {
        val menu = Intent(this, MenuActivity::class.java)
        startActivity(menu)
    }

}