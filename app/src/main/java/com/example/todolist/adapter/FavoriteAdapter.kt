package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemTaskFavoriteBinding
import com.example.todolist.model.Task

class FavoriteAdapter(
    private var list: MutableList<Task>,
    private val onClick: (Task) -> Unit,
    private val onUnfavorite: (Task) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavViewHolder>() {

    inner class FavViewHolder(val binding: ItemTaskFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = ItemTaskFavoriteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val item = list[position]

        holder.binding.txtTitle.text = item.title
        holder.binding.txtDate.text = item.date

        // klik card → buka detail
        holder.itemView.setOnClickListener {
            onClick(item)
        }

        // klik icon un-fav
        holder.binding.btnUnfav.setOnClickListener {

            // hapus dari favorite list adapter
            val removedItem = list[position]
            removedItem.isFavorite = false

            onUnfavorite(removedItem)

            list.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, list.size)
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: MutableList<Task>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}
