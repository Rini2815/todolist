package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.model.Task

class TaskAdapter(
    private val list: MutableList<Task>,
    private val onClick: (Task) -> Unit,
    private val onToggle: (Task, Boolean) -> Unit = { _, _ -> } // default kosong supaya opsional
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvTime: TextView = v.findViewById(R.id.tvTaskTime)
        val tvTitle: TextView = v.findViewById(R.id.tvTaskTitle)
        val switchDone: Switch = v.findViewById(R.id.switchDone)
        val btnDetail: ImageView = v.findViewById(R.id.btnDetailTask)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.tvTime.text = item.time
        holder.tvTitle.text = item.title

        // Hindari event terpicu ulang
        holder.switchDone.setOnCheckedChangeListener(null)
        holder.switchDone.isChecked = item.isDone

        holder.switchDone.setOnCheckedChangeListener { _, state ->
            onToggle(item, state)
        }

        holder.btnDetail.setOnClickListener {
            onClick(item)
        }
    }
}
