package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.list_game.*
import java.util.*
import kotlin.collections.ArrayList

class GameFragment: Fragment() {

    companion object {
        var gameList = ArrayList<Game>()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addgame.setOnClickListener{
            val intent = Intent(activity, AddGameActivity::class.java)
            startActivityForResult(intent, 1)
        }

        reco.setOnClickListener{
            val intent = Intent(activity, RecommendationsActivity::class.java)
            startActivityForResult(intent, 1)
        }


        rv_list.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL, false)

        rv_list.adapter = GameRecyclerAdapter(gameList){
            // Code qui s’exécute quand on touche un élément
            // it= le Message de la ligne touchée
            Toast.makeText(activity, "Click sur : ${it.name} = ${it.date}", Toast.LENGTH_SHORT)
        }

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_game, container, false)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    val r = data?.getBooleanExtra(AddGameActivity.NAME, true)?: false
                    if(r) {
                        //user_choice.text = data?.getStringExtra(ConfirmationActivity.EXTRA_ISCONFIRMED).toString()
                        gameList.add(Game(
                            data?.getStringExtra(AddGameActivity.NAME).toString(),
                            data?.getStringExtra(AddGameActivity.CATEGORY).toString(),
                            data?.getStringExtra(AddGameActivity.DATE).toString()))
                        rv_list.adapter?.notifyDataSetChanged()


                    }
                }
            }
        }
    }
}