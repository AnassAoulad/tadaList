package com.example.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {
    var currentList: List<Task> = emptyList()
    var onClickEdit: (Task) -> Unit = {}
    var onClickDelete: (Task) -> Unit = {}

    // on utilise `inner` ici afin d'avoir accès aux propriétés de l'adapter directement
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task) {

            val textTitleView = itemView.findViewById<TextView>(R.id.task_title)
            textTitleView.text = task.title

            val textDescView = itemView.findViewById<TextView>(R.id.task_description)
            textDescView.text = task.description

            val deleteButton = itemView.findViewById<ImageButton>(R.id.deleteButton)
            deleteButton.setOnClickListener{ onClickDelete(task) }

            val editButton = itemView.findViewById<ImageButton>(R.id.editButton)
            editButton.setOnClickListener{ onClickEdit(task) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(this.currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

}