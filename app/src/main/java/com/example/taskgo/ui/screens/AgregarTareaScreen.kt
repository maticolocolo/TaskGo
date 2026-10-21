package com.example.taskgo.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AgregarTareaScreen(
    onGuardar: (String, String) -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var mostrarErrores by remember { mutableStateOf(false) }

    val tituloInvalido = titulo.isBlank()
    val descripcionInvalida = descripcion.isBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Nueva tarea",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título de la tarea") },
            isError = mostrarErrores && tituloInvalido,
            supportingText = {
                if (mostrarErrores && tituloInvalido) {
                    Text("El título es obligatorio")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            isError = mostrarErrores && descripcionInvalida,
            supportingText = {
                if (mostrarErrores && descripcionInvalida) {
                    Text("La descripción es obligatoria")
                }
            },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                mostrarErrores = true

                if (!tituloInvalido && !descripcionInvalida) {
                    onGuardar(titulo.trim(), descripcion.trim())
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar tarea")
        }
    }
}
