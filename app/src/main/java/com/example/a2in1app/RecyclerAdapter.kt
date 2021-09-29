package com.example.a2in1app

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_num_game.view.*

class RecyclerAdapter (val mesgges:ArrayList<String>):RecyclerView.Adapter<RecyclerAdapter.itemViewHolder> () {
    class itemViewHolder (itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder {
        return itemViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.item_num_game,parent,false))
    }

    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {
        val msg=mesgges[position]
        holder.itemView.apply {
            textView11.text=msg
            if(msg.startsWith("Found")){
                textView11.setTextColor(Color.GREEN)
            } else if (msg.startsWith("No",true)|| msg.startsWith("Wrong",true)){
                textView11.setTextColor(Color.RED)
            } else
                textView11.setTextColor(Color.BLACK)
        }
    }

    override fun getItemCount(): Int= mesgges.size

}