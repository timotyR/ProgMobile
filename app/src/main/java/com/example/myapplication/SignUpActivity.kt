package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MainActivity.Companion.users
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity : AppCompatActivity() {

    companion object {
        const val USER = "user"
        const val PASSWORD = "password"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)



        ok.setOnClickListener {
            if(users.containsKey(username.text.toString()))
            {
                Toast.makeText(this, "Username already exists !", Toast.LENGTH_SHORT).show()
            }
            else if(!password.text.toString().equals(confirmpassword.text.toString()))
            {
                Toast.makeText(this, "Passwords must be the same !", Toast.LENGTH_SHORT).show()

            }
            else if(password.text.toString().length < 8)
            {
                Toast.makeText(this, "Password must have at least 8 characters !", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val intent = Intent()
                intent.putExtra(USER, true)
                intent.putExtra(USER, username.text.toString())
                intent.putExtra(PASSWORD, password.text.toString())

                setResult(RESULT_OK, intent)
                finish()
            }
        }

        cancel.setOnClickListener {
            val intent = Intent()
            intent.putExtra(USER, false)

            setResult(RESULT_OK, intent)
            finish()

        }
    }


}