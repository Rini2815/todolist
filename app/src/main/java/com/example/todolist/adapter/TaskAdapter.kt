package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemTaskBinding
import com.example.todolist.model.Task

class TaskAdapter(
    private var list: MutableList<Task>,
    private val onClick: (Task) -> Unit,
    private val onFavorite: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = list[position]

        holder.binding.txtTitle.text = item.title
        holder.binding.txtDate.text = item.date
        holder.binding.txtCategory.text = item.category

        // klik masuk ke halaman detail
        holder.itemView.setOnClickListener {
            onClick(item)
        }

        // tombol favorit
        holder.binding.btnFavorite.setOnClickListener {
            item.isFavorite = !item.isFavorite
            notifyItemChanged(position)
            onFavorite(item)
        }

        // icon favorit
        val icon = if (item.isFavorite) {
            com.example.todolist.R.drawable.ic_favorite_filled
        } else {
            com.example.todolist.R.drawable.ic_favorite_border
        }

        holder.binding.btnFavorite.setImageResource(icon)
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: MutableList<Task>) {
        list = newList
        notifyDataSetChanged()
    }
}