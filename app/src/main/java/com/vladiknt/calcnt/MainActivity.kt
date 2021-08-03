package com.vladiknt.calcnt

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var isResult = true
    private var expression = ""
    private var memory = ""

    companion object {
        var vibrator: Vibrator? = null // for vibration while tapping
        fun vibrate() {
            if (Build.VERSION.SDK_INT >= 26)
                vibrator?.vibrate(VibrationEffect.createOneShot(3, VibrationEffect.DEFAULT_AMPLITUDE))
            else
                vibrator?.vibrate(3)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
    }

    fun clear(view: View?) {
        vibrate()
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
        vibrate()
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
        vibrate()
        val newView = view as TextView
        if (isResult) {
            when (newView.text) {
                "+", "-", "×", "÷", "xª" -> {}
                else -> expression = ""
            }
            isResult = false
        }
        if (newView.text == "xª")
            expression += "^"
        else
            expression += newView.text
        findViewById<TextView>(R.id.result).text = expression
    }

    fun saveValue(view: View?) {
        vibrate()
        memory = expression
        findViewById<TextView>(R.id.memory).text = memory
    }

    fun pasteValue(view: View?) {
        vibrate()
        expression += memory
        findViewById<TextView>(R.id.result).text = expression
    }

    fun menuButton(view: View?) {
        vibrate()
        val menu = Intent(this, InfoActivity::class.java)
        startActivity(menu)
    }

}