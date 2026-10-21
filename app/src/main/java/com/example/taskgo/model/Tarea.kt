package com.example.taskgo.model

data class Tarea(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val completada: Boolean = false
)
