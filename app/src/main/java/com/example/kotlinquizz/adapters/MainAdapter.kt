package com.example.kotlinquizz.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinquizz.R
import com.example.kotlinquizz.models.Game

class MainAdapter(private val mDataSet: ArrayList<Game>, var clickAction: (String) -> Unit) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.game_block, parent, false)
        return MainViewHolder(v)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = mDataSet[position]
        holder.bindItems(data)

        holder.itemView.setOnClickListener {
            clickAction(data.name)
        }
    }

    override fun getItemCount(): Int {
        return mDataSet.size ?: 0
    }

    inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val name = v.findViewById(R.id.tvName) as TextView
        private val score = v.findViewById(R.id.tvScore) as TextView

        fun bindItems(data: Game) {
            name.text = data.name
            score.text = data.score.toString()
        }
    }
}