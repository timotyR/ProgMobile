package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GameRecyclerAdapter(val list: List<Game>,
                          val listener: (Game) -> Unit) :
    RecyclerView.Adapter<GameRecyclerAdapter.GameViewHolder>() {

    inner class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.game_name)
        var category: TextView = view.findViewById(R.id.game_category)
        var date: TextView = view.findViewById(R.id.game_date)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.name.text = list[position].name
        holder.category.text = list[position].category
        holder.date.text = list[position].date

        holder.itemView.setOnClickListener{ listener(list[position]) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GameViewHolder(LayoutInflater.from(parent.context).
            inflate(R.layout.list_game, parent, false))

    override fun getItemCount() = list.size
}