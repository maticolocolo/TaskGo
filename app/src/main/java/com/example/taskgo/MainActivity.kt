
package com.example.taskgo

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskgo.model.Tarea
import com.example.taskgo.ui.screens.AgregarTareaScreen
import com.example.taskgo.ui.screens.EstadisticasScreen
import com.example.taskgo.ui.theme.TaskGoTheme
import com.example.taskgo.viewmodel.TareaViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TaskGoTheme {
                TaskGoApp()
            }
        }
    }
}

@Composable
fun TaskGoApp(
    tareaViewModel: TareaViewModel = viewModel()
) {
    val context = LocalContext.current

    val solicitarNotificaciones =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.RequestPermission(),
            onResult = { }
        )

    LaunchedEffect(Unit) {
        RecursosNativos.crearCanalNotificaciones(context)

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.TIRAMISU
        ) {
            solicitarNotificaciones.launch(
                Manifest.permission.POST_NOTIFICATIONS
            )
        }
    }

    var pantalla by remember {
        mutableStateOf("inicio")
    }

    BackHandler(enabled = pantalla != "inicio") {
        pantalla = "inicio"
    }

    Scaffold(
        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    selected = pantalla == "inicio",
                    onClick = {
                        pantalla = "inicio"
                    },
                    icon = {
                        Text("☰")
                    },
                    label = {
                        Text("Inicio")
                    }
                )

                NavigationBarItem(
                    selected = pantalla == "agregar",
                    onClick = {
                        pantalla = "agregar"
                    },
                    icon = {
                        Text("+")
                    },
                    label = {
                        Text("Agregar")
                    }
                )

                NavigationBarItem(
                    selected = pantalla == "estadisticas",
                    onClick = {
                        pantalla = "estadisticas"
                    },
                    icon = {
                        Text("◉")
                    },
                    label = {
                        Text("Estadísticas")
                    }
                )
            }
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            when (pantalla) {

                "inicio" -> {
                    PantallaInicio(
                        tareas = tareaViewModel.tareas,

                        onAgregar = {
                            pantalla = "agregar"
                        },

                        onCambiarEstado = { tarea ->
                            tareaViewModel.cambiarEstado(tarea)

                            if (!tarea.completada) {
                                RecursosNativos.vibrar(context)
                            }
                        },

                        onEliminar = { tarea ->
                            tareaViewModel.eliminarTarea(tarea)
                        },

                        onEditar = { tarea, titulo, descripcion ->
                            tareaViewModel.editarTarea(
                                tarea,
                                titulo,
                                descripcion
                            )
                        }
                    )
                }

                "agregar" -> {
                    AgregarTareaScreen(
                        onGuardar = { titulo, descripcion ->

                            tareaViewModel.agregarTarea(
                                titulo,
                                descripcion
                            )

                            RecursosNativos.notificarTareaCreada(
                                context,
                                titulo
                            )

                            pantalla = "inicio"
                        }
                    )
                }

                "estadisticas" -> {
                    EstadisticasScreen(
                        tareas = tareaViewModel.tareas
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicio(
    tareas: SnapshotStateList<Tarea>,
    onAgregar: () -> Unit,
    onCambiarEstado: (Tarea) -> Unit,
    onEliminar: (Tarea) -> Unit,
    onEditar: (Tarea, String, String) -> Unit
) {
    var tareaParaEliminar by remember {
        mutableStateOf<Tarea?>(null)
    }

    var tareaParaEditar by remember {
        mutableStateOf<Tarea?>(null)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("TaskGo")
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = onAgregar
            ) {
                Text("+")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                text = "Mis tareas",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Total de tareas: ${tareas.size}"
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            AnimatedVisibility(
                visible = tareas.isEmpty(),
                enter = fadeIn(tween(400)),
                exit = fadeOut(tween(200))
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No tienes tareas todavía")
                }
            }

            LazyColumn(
                verticalArrangement =
                    Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {

                items(
                    items = tareas,
                    key = { it.id }
                ) { tarea ->

                    TarjetaTareaAnimada(
                        tarea = tarea,

                        onCambiarEstado = {
                            onCambiarEstado(tarea)
                        },

                        onEditar = {
                            tareaParaEditar = tarea
                        },

                        onEliminar = {
                            tareaParaEliminar = tarea
                        },

                        modifier = Modifier.animateItem()
                    )
                }
            }
        }
    }

    tareaParaEliminar?.let { tarea ->

        AlertDialog(
            onDismissRequest = {
                tareaParaEliminar = null
            },

            title = {
                Text("Eliminar tarea")
            },

            text = {
                Text(
                    "¿Seguro que quieres eliminar " +
                            "\"${tarea.titulo}\"?"
                )
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        onEliminar(tarea)
                        tareaParaEliminar = null
                    }
                ) {
                    Text("Eliminar")
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        tareaParaEliminar = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    tareaParaEditar?.let { tarea ->

        DialogoEditarTarea(
            tarea = tarea,

            onGuardar = { titulo, descripcion ->
                onEditar(
                    tarea,
                    titulo,
                    descripcion
                )

                tareaParaEditar = null
            },

            onCancelar = {
                tareaParaEditar = null
            }
        )
    }
}

@Composable
fun TarjetaTareaAnimada(
    tarea: Tarea,
    onCambiarEstado: () -> Unit,
    onEditar: () -> Unit,
    onEliminar: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colorTarjeta by animateColorAsState(
        targetValue =
            if (tarea.completada)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surfaceVariant,

        animationSpec = tween(500),
        label = "colorTarea"
    )

    val escala by animateFloatAsState(
        targetValue =
            if (tarea.completada) 0.98f else 1f,

        animationSpec = tween(300),
        label = "escalaTarea"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = escala
                scaleY = escala
            },

        colors = CardDefaults.cardColors(
            containerColor = colorTarjeta
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = tarea.completada,
                    onCheckedChange = {
                        onCambiarEstado()
                    }
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = tarea.titulo,
                        style = MaterialTheme
                            .typography.titleMedium
                    )

                    Text(
                        text = tarea.descripcion,
                        style = MaterialTheme
                            .typography.bodyMedium
                    )

                    Text(
                        text =
                            if (tarea.completada)
                                "Completada"
                            else
                                "Pendiente",

                        style = MaterialTheme
                            .typography.labelSmall
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                TextButton(
                    onClick = onEditar
                ) {
                    Text("Editar")
                }

                TextButton(
                    onClick = onEliminar
                ) {
                    Text("Eliminar")
                }
            }
        }
    }
}

@Composable
fun DialogoEditarTarea(
    tarea: Tarea,
    onGuardar: (String, String) -> Unit,
    onCancelar: () -> Unit
) {
    var titulo by remember(tarea.id) {
        mutableStateOf(tarea.titulo)
    }

    var descripcion by remember(tarea.id) {
        mutableStateOf(tarea.descripcion)
    }

    val datosValidos =
        titulo.isNotBlank() &&
                descripcion.isNotBlank()

    AlertDialog(
        onDismissRequest = onCancelar,

        title = {
            Text("Editar tarea")
        },

        text = {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                OutlinedTextField(
                    value = titulo,
                    onValueChange = {
                        titulo = it
                    },
                    label = {
                        Text("Título")
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = {
                        descripcion = it
                    },
                    label = {
                        Text("Descripción")
                    },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )

                if (!datosValidos) {
                    Text(
                        text = "Completa ambos campos",
                        color = MaterialTheme
                            .colorScheme.error
                    )
                }
            }
        },

        confirmButton = {
            TextButton(
                enabled = datosValidos,

                onClick = {
                    onGuardar(
                        titulo.trim(),
                        descripcion.trim()
                    )
                }
            ) {
                Text("Guardar cambios")
            }
        },

        dismissButton = {
            TextButton(
                onClick = onCancelar
            ) {
                Text("Cancelar")
            }
        }
    )
}
