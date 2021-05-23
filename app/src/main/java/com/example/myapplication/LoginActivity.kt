package com.example.myapplication



import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.GamesActivity.Companion.CURRENT_USER
import com.example.myapplication.MainActivity.Companion.users
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        go.setOnClickListener {
            if(users.containsKey(login.text.toString())
                && users.get(login.text.toString()).equals(pwd.text.toString()))
            {
                CURRENT_USER = login.text.toString()
                val intent = Intent(this, GamesActivity::class.java)
                startActivity(intent)
            }
            else
            {
                Toast.makeText(this, "Authentication failed !" , Toast.LENGTH_SHORT).show()
            }
        }

        back.setOnClickListener {
            finish()
        }
    }
}