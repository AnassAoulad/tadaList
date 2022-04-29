package network

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasklist.R
import com.example.tasklist.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TasksListViewModel : ViewModel() {
    private val webService = Api.tasksWebService

    // privée mais modifiable à l'intérieur du VM:
    private val _tasksStateFlow = MutableStateFlow<List<Task>>(emptyList())
    // même donnée mais publique et non-modifiable à l'extérieur afin de pouvoir seulement s'y abonner:
    public val tasksStateFlow: StateFlow<List<Task>> = _tasksStateFlow.asStateFlow()

    fun refresh() {
        viewModelScope.launch {
            val response = webService.getTasks() // Call HTTP (opération longue)
            if (response.isSuccessful) { // à cette ligne, on a reçu la réponse de l'API
                Log.e("Network", "Error: ${response.message()}")
            }
            val fetchedTasks = response.body()!!
            _tasksStateFlow.value = fetchedTasks // on modifie le flow, ce qui déclenche ses observers
        }
    }

    // à compléter plus tard:
    fun create(task: Task) {}
    fun update(task: Task) {}
    fun delete(task: Task) {}
}