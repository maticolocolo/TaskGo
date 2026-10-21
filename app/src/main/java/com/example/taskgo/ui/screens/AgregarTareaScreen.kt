
package com.example.taskgo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.taskgo.validation.TareaValidator

@Composable
fun AgregarTareaScreen(
    onGuardar: (String, String) -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var mostrarErrores by remember { mutableStateOf(false) }

    val resultado = TareaValidator.validar(titulo, descripcion)

    val errorTitulo = mostrarErrores &&
            resultado.errorTitulo != null

    val errorDescripcion = mostrarErrores &&
            resultado.errorDescripcion != null

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
            isError = errorTitulo,
            trailingIcon = {
                if (errorTitulo) {
                    Icon(
                        imageVector = Icons.Filled.ErrorOutline,
                        contentDescription = "Error en el título",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            supportingText = {
                if (errorTitulo) {
                    Text(
                        text = resultado.errorTitulo.orEmpty(),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            isError = errorDescripcion,
            trailingIcon = {
                if (errorDescripcion) {
                    Icon(
                        imageVector = Icons.Filled.ErrorOutline,
                        contentDescription = "Error en la descripción",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            supportingText = {
                if (errorDescripcion) {
                    Text(
                        text = resultado.errorDescripcion.orEmpty(),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                mostrarErrores = true

                if (resultado.esValido) {
                    onGuardar(
                        titulo.trim(),
                        descripcion.trim()
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar tarea")
        }
    }
}
