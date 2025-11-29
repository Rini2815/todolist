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
    private val onToggle: (Task, Boolean) -> Unit
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvTime: TextView = v.findViewById(R.id.tvTime)
        val tvTitle: TextView = v.findViewById(R.id.tvTitle)
        val switchDone: Switch = v.findViewById(R.id.switchDone)
        val btnDetail: ImageView = v.findViewById(R.id.btnDetail)
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
        holder.switchDone.isChecked = item.isDone

        // toggle switch “selesai/belum”
        holder.switchDone.setOnCheckedChangeListener { _, state ->
            onToggle(item, state)
        }

        // klik card → menuju DetailTaskActivity
        holder.btnDetail.setOnClickListener {
            onClick(item)
        }
    }
}