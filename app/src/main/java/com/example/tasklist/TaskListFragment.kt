package com.example.tasklist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.form.FormActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import network.Api
import java.util.*

class TaskListFragment : Fragment() {

    private var taskList = listOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )
    private val adapter = TaskListAdapter()

    val createTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = result.data?.getSerializableExtra("task") as Task? ?:return@registerForActivityResult
        taskList = taskList + task
        adapter.currentList = taskList
        adapter.notifyDataSetChanged()
    }

    val editTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = result.data?.getSerializableExtra("task") as Task? ?:return@registerForActivityResult
        taskList = taskList.map { if (it.id == task.id) task else it }
        adapter.currentList = taskList
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)
        adapter.currentList = taskList
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = adapter

        val addButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        addButton.setOnClickListener {
            val intent = Intent(context, FormActivity::class.java)
            createTask.launch(intent)
        /*
            val newTask = Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")
            taskList = taskList + newTask
            adapter.currentList = taskList
            adapter.notifyDataSetChanged()
        */
        }

        adapter.onClickDelete = { task-> taskList = taskList - task
            refreshAdapter()}

        adapter.onClickEdit = { task->
            val intent = Intent(context, FormActivity::class.java)
            intent.putExtra("task", task)
            editTask.launch(intent)

        }
    }
    private fun refreshAdapter() {
        adapter.currentList = taskList
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch{

            val userInfo = Api.userWebService.getInfo().body()!!
            val userInfoTextView = view?.findViewById<TextView>(R.id.nameView)
            userInfoTextView?.text = "${userInfo.firstName} ${userInfo.lastName}"

        }
    }
}
