
package com.example.taskgo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskgo.model.Tarea

@Composable
fun EstadisticasScreen(
    tareas: List<Tarea>
) {
    val total = tareas.size
    val completadas = tareas.count { it.completada }
    val pendientes = total - completadas

    val porcentaje = if (total > 0) {
        completadas.toFloat() / total
    } else {
        0f
    }

    val progresoAnimado by animateFloatAsState(
        targetValue = porcentaje,
        animationSpec = tween(700),
        label = "progreso"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Mis estadísticas",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Revisa cuánto has avanzado con tus tareas.",
            style = MaterialTheme.typography.bodyMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Progreso general",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "${(porcentaje * 100).toInt()}%",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                LinearProgressIndicator(
                    progress = { progresoAnimado },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                )

                Text(
                    text = "$completadas de $total tareas completadas"
                )
            }
        }

        TarjetaEstadistica(
            titulo = "Total de tareas",
            cantidad = total.toString()
        )

        TarjetaEstadistica(
            titulo = "Tareas completadas",
            cantidad = completadas.toString()
        )

        TarjetaEstadistica(
            titulo = "Tareas pendientes",
            cantidad = pendientes.toString()
        )

        if (total == 0) {
            Text(
                text = "Agrega tu primera tarea para ver tu progreso.",
                style = MaterialTheme.typography.bodyMedium
            )
        } else if (pendientes == 0) {
            Text(
                text = "¡Excelente! Completaste todas tus tareas.",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun TarjetaEstadistica(
    titulo: String,
    cantidad: String
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = cantidad,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
