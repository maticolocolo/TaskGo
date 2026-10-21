
package com.example.taskgo.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.taskgo.database.TaskGoDatabase
import com.example.taskgo.database.TareaEntity
import com.example.taskgo.model.Tarea
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TareaViewModel(application: Application) : AndroidViewModel(application) {

    private val database = Room.databaseBuilder(
        application,
        TaskGoDatabase::class.java,
        "taskgo_database"
    ).build()

    private val tareaDao = database.tareaDao()

    val tareas = mutableStateListOf<Tarea>()

    init {
        viewModelScope.launch {
            tareaDao.obtenerTareas().collectLatest { lista ->
                tareas.clear()
                tareas.addAll(
                    lista.map { entidad ->
                        Tarea(
                            id = entidad.id,
                            titulo = entidad.titulo,
                            descripcion = entidad.descripcion,
                            completada = entidad.completada
                        )
                    }
                )
            }
        }
    }

    fun agregarTarea(titulo: String, descripcion: String) {
        viewModelScope.launch {
            tareaDao.insertarTarea(
                TareaEntity(
                    titulo = titulo,
                    descripcion = descripcion
                )
            )
        }
    }

    fun cambiarEstado(tarea: Tarea) {
        viewModelScope.launch {
            tareaDao.actualizarTarea(
                TareaEntity(
                    id = tarea.id,
                    titulo = tarea.titulo,
                    descripcion = tarea.descripcion,
                    completada = !tarea.completada
                )
            )
        }
    }

    fun eliminarTarea(tarea: Tarea) {
        viewModelScope.launch {
            tareaDao.eliminarTarea(
                TareaEntity(
                    id = tarea.id,
                    titulo = tarea.titulo,
                    descripcion = tarea.descripcion,
                    completada = tarea.completada
                )
            )
        }
    }

    fun editarTarea(
        tarea: Tarea,
        nuevoTitulo: String,
        nuevaDescripcion: String
    ) {
        viewModelScope.launch {
            tareaDao.actualizarTarea(
                TareaEntity(
                    id = tarea.id,
                    titulo = nuevoTitulo.trim(),
                    descripcion = nuevaDescripcion.trim(),
                    completada = tarea.completada
                )
            )
        }
    }
}
