package com.vladiknt.calcnt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }

    fun infoButton(view: View?) {
        val info = Intent(this, InfoActivity::class.java)
        startActivity(info)
    }

    fun helpButton(view: View?) {
        val help = Intent(this, HelpActivity::class.java)
        startActivity(help)
    }

}