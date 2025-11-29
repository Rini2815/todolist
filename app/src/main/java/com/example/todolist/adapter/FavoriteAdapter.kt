package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.model.Task

class FavoriteAdapter(
    private val list: MutableList<Task>,
    private val onClick: (Task) -> Unit,
    private val onUnfavorite: (Task) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvTitle: TextView = v.findViewById(R.id.tvFavTitle)
        val tvDesc: TextView = v.findViewById(R.id.tvFavDesc)
        val btnUnfav: ImageButton = v.findViewById(R.id.btnUnfav)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.tvTitle.text = item.title
        holder.tvDesc.text = item.description

        // klik card → buka detail tugas
        holder.itemView.setOnClickListener {
            onClick(item)
        }

        // unfavorite
        holder.btnUnfav.setOnClickListener {
            onUnfavorite(item)
        }
    }
}