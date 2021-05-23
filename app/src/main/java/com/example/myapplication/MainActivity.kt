package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.Telephony.Mms.Part.TEXT
import com.example.myapplication.MyDataBase.Companion.CATEGORY
import com.example.myapplication.MyDataBase.Companion.DATE
import com.example.myapplication.MyDataBase.Companion.JOUEURS_LOGIN
import com.example.myapplication.MyDataBase.Companion.JOUEURS_MDP
import com.example.myapplication.MyDataBase.Companion.NAME
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

import java.sql.Types.INTEGER
import java.util.HashMap

val Context.myDataBase: MyDataBase
    get() = MyDataBase.getInstance(this)

class MyDataBase private constructor(ctx: Context) : ManagedSQLiteOpenHelper(ctx,
    DATABASE_NAME, null, 1) {
    init {
        instance = this
    }

    companion object {
        const val DATABASE_NAME = "MyDataBase.db" // fichier reel du stockage interne
        const val JOUEURS = "joueurs"
        const val JOUEURS_LOGIN = "login"
        const val JOUEURS_MDP = "mdp"
        const val GAMES = "games"
        const val NAME = "name"
        const val CATEGORY = "category"
        const val DATE = "date"

        private var instance: MyDataBase? = null
        @Synchronized
        fun getInstance(ctx: Context) = instance ?: MyDataBase(ctx.applicationContext)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            JOUEURS, true,
            JOUEURS_LOGIN to  SqlType.create("TEXT PRIMARY KEY NOT NULL"),//org.jetbrains.anko.db.TEXT + PRIMARY_KEY + NOT_NULL,
            JOUEURS_MDP to SqlType.create("TEXT NOT NULL")//org.jetbrains.anko.db.TEXT + NOT_NULL,
        )

        /*db.createTable(
            GAMES, true,
            JOUEURS_LOGIN to  SqlType.create("TEXT NOT NULL"),//org.jetbrains.anko.db.TEXT + PRIMARY_KEY + NOT_NULL,
            NAME to SqlType.create("TEXT NOT NULL"),//org.jetbrains.anko.db.TEXT + NOT_NULL,
            CATEGORY to SqlType.create("TEXT NOT NULL"),//org.jetbrains.anko.db.TEXT + NOT_NULL,
            DATE to SqlType.create("TEXT NOT NULL") //org.jetbrains.anko.db.TEXT + NOT_NULL,

        )*/
        val CREATE_JOURNEY_TABLE = ("CREATE TABLE " + GAMES + "("
                + JOUEURS_LOGIN + " TEXT, "
                + NAME + " TEXT, "
                + CATEGORY + " TEXT, "
                + DATE + " TEXT, " +
                "FOREIGN KEY (" + JOUEURS_LOGIN + ") REFERENCES " +
                JOUEURS + "(" + JOUEURS_LOGIN + ")"
                + " PRIMARY KEY (" + JOUEURS_LOGIN +", "+NAME+")" + ")")
        db.execSQL(CREATE_JOURNEY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

class MainActivity : AppCompatActivity() {

    companion object {
        var users = HashMap<String,String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        signup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivityForResult(intent, 1)
        }

        myDataBase.use {
                val result = select(MyDataBase.JOUEURS,
                JOUEURS_LOGIN,
                JOUEURS_MDP).exec {

                    for (row in asMapSequence()) {
                        users.put(
                            row[JOUEURS_LOGIN] as String,
                                row[JOUEURS_MDP] as String
                        )
                    }
                }
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                val r = data?.getBooleanExtra(SignUpActivity.USER, true)?: false
                if(r) {
                    users.put(data?.getStringExtra(SignUpActivity.USER).toString(),
                        data?.getStringExtra(SignUpActivity.PASSWORD).toString())
                    Toast.makeText(this, "Account successfully created !",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()

        for ((key,value) in users)
        {
            myDataBase.use {
                insert(MyDataBase.JOUEURS,
                    JOUEURS_LOGIN to key,
                    JOUEURS_MDP to value)

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
