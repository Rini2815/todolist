package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
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

        holder.binding.apply {
            txtTitle.text = item.title
            txtDate.text = item.date
            txtCategory.text = item.category

            // Klik item → ke detail
            root.setOnClickListener { onClick(item) }

            // Klik favorit
            btnFavorite.setOnClickListener {
                item.isFavorite = !item.isFavorite
                notifyItemChanged(position)
                onFavorite(item)
            }

            // Icon favorit
            val iconRes = if (item.isFavorite)
                R.drawable.outline_ac_unit_24
            else
                R.drawable.outline_ac_unit_24

            btnFavorite.setImageResource(iconRes)
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: MutableList<Task>) {
        list = newList
        notifyDataSetChanged()
    }
}
