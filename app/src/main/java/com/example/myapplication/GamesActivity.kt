package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.GameFragment.Companion.gameList
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_game.*
import org.jetbrains.anko.db.asMapSequence
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class GamesActivity : AppCompatActivity() {

    companion object {
        var CURRENT_USER = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)
        Toast.makeText(this, "Welcome " + CURRENT_USER + " !" , Toast.LENGTH_SHORT).show()

        rv_list.adapter = GameRecyclerAdapter(gameList){
            // Code qui s’exécute quand on touche un élément
            // it= le Message de la ligne touchée
            toast("Click sur : ${it.name} = ${it.date}")
        }
    }

    override fun onStart() {
        super.onStart()
        myDataBase.use {
            val result = select(
                MyDataBase.GAMES,
                MyDataBase.NAME,
                MyDataBase.CATEGORY,
                MyDataBase.DATE
            ).whereArgs("(${MyDataBase.JOUEURS_LOGIN} = {exp})",
                "exp" to CURRENT_USER)
                .exec {

                    for (row in asMapSequence()) {
                        gameList.add(
                            Game(
                                row[MyDataBase.NAME] as String,
                                row[MyDataBase.CATEGORY] as String,
                                row[MyDataBase.DATE] as String
                            )
                        )
                    }
                }
        }
    }

    override fun onStop() {
        super.onStop()

        for (g in gameList)
        {
            myDataBase.use {
                insert(MyDataBase.GAMES,
                    MyDataBase.JOUEURS_LOGIN to CURRENT_USER,
                    MyDataBase.NAME to g.name,
                    MyDataBase.CATEGORY to g.category,
                    MyDataBase.DATE to g.date)
            }
        }
        gameList.removeAll(gameList)
    }

    override fun onDestroy() {
        super.onDestroy()
        //Toast.makeText(this, "Test onDestroy", Toast.LENGTH_SHORT).show()

    }
}