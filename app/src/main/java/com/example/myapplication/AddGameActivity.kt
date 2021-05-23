package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_addgame.*

class AddGameActivity : AppCompatActivity() {

    companion object {
        const val NAME = "name"
        const val CATEGORY = "category"
        const val DATE = "date"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addgame)

        validategame.setOnClickListener {
            val intent = Intent()
            intent.putExtra(NAME, true)
            intent.putExtra(NAME, entergamename.text.toString())
            intent.putExtra(CATEGORY, entergamecategory.text.toString())
            intent.putExtra(DATE, entergamedate.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }

        cancelgame.setOnClickListener {
            val intent = Intent()
            intent.putExtra(NAME, false)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}