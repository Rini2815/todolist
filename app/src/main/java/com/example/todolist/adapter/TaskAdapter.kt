package com.example.todolist.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemTaskBinding
import com.example.todolist.model.Task

class TaskAdapter(
    private val list: MutableList<Task>,
    private val onClick: (Task) -> Unit,
    private val onToggle: (Task, Boolean) -> Unit = { _, _ -> }
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        with(holder.binding) {
            // Set data
            tvTaskTime.text = item.time
            tvTaskTitle.text = item.title

            // Set switch state tanpa trigger listener
            switchDone.setOnCheckedChangeListener(null)
            switchDone.isChecked = item.isDone

            // Apply strikethrough jika task selesai
            if (item.isDone) {
                tvTaskTitle.paintFlags = tvTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                tvTaskTitle.alpha = 0.5f
                tvTaskTime.alpha = 0.5f
            } else {
                tvTaskTitle.paintFlags = tvTaskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                tvTaskTitle.alpha = 1f
                tvTaskTime.alpha = 1f
            }

            // Set listener untuk switch
            switchDone.setOnCheckedChangeListener { _, isChecked ->
                onToggle(item, isChecked)

                // Update visual immediately
                if (isChecked) {
                    tvTaskTitle.paintFlags = tvTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    tvTaskTitle.alpha = 0.5f
                    tvTaskTime.alpha = 0.5f
                } else {
                    tvTaskTitle.paintFlags = tvTaskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    tvTaskTitle.alpha = 1f
                    tvTaskTime.alpha = 1f
                }
            }

            // Klik card -> Detail
            root.setOnClickListener {
                onClick(item)
            }
        }
    }
}