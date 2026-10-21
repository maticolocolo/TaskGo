# TaskGo - Aplicación de Gestión de Tareas

## Descripción del proyecto

**TaskGo** es una aplicación móvil desarrollada para dispositivos Android que permite organizar, administrar y realizar un seguimiento de las tareas personales de manera sencilla e intuitiva.

El proyecto busca solucionar la problemática de la desorganización de actividades cotidianas, proporcionando una herramienta digital que permite registrar tareas, consultar su información, modificarlas, eliminarlas y marcarlas como completadas.

La aplicación fue desarrollada utilizando **Kotlin y Jetpack Compose**, incorporando Material Design 3, arquitectura MVVM, almacenamiento local mediante Room, animaciones y recursos nativos del dispositivo.

- Institución: Duoc UC
- Asignatura: Desarrollo de Aplicaciones Móviles DSY1105
- Proyecto: TaskGo
- Integrantes: Matías Herrera

## Objetivo general

Desarrollar una aplicación móvil Android que facilite la organización y seguimiento de tareas personales mediante una interfaz intuitiva, gestión de estado, persistencia local y funcionalidades interactivas.

## Objetivos específicos

- Implementar una interfaz moderna utilizando Material Design 3.
- Permitir la creación, visualización, edición y eliminación de tareas.
- Incorporar validaciones para evitar el ingreso de información vacía.
- Utilizar arquitectura MVVM para organizar las responsabilidades del proyecto.
- Almacenar las tareas localmente mediante Room.
- Integrar notificaciones y vibración como recursos nativos.
- Implementar animaciones que mejoren la experiencia del usuario.
- Visualizar estadísticas sobre el cumplimiento de las tareas.

## Tecnologías utilizadas

| Tecnología | Utilización |
|---|---|
| Kotlin | Lenguaje principal de programación |
| Android Studio | Entorno de desarrollo |
| Jetpack Compose | Construcción de interfaces |
| Material Design 3 | Diseño visual |
| MVVM | Organización de la aplicación |
| Room | Persistencia local de datos |
| SQLite | Motor de base de datos utilizado por Room |
| Coroutines | Ejecución de operaciones asíncronas |
| State de Compose | Gestión reactiva de la interfaz |
| NotificationManager | Notificaciones del dispositivo |
| Vibrator / VibrationEffect | Retroalimentación mediante vibración |
| GitHub | Repositorio del proyecto y control de versiones |

## Funcionalidades implementadas

### 1. Gestión de tareas (CRUD)

La aplicación permite:

- Crear tareas con título y descripción.
- Visualizar las tareas registradas.
- Editar el título y la descripción de una tarea.
- Eliminar tareas.
- Marcar tareas como completadas o pendientes.

### 2. Validación de formularios

El formulario de creación comprueba que el título y la descripción no estén vacíos.

Cuando el usuario intenta guardar una tarea sin completar los campos obligatorios, la aplicación muestra mensajes de error y resalta los campos correspondientes.

### 3. Persistencia local

TaskGo utiliza Room para almacenar la información de las tareas en una base de datos local.

Esto permite conservar las tareas registradas incluso después de cerrar y volver a abrir la aplicación.

### 4. Navegación entre pantallas

La aplicación cuenta con tres pantallas principales:

**Inicio:** permite consultar y administrar las tareas.

**Agregar tarea:** contiene el formulario para registrar nuevas tareas.

**Estadísticas:** presenta información sobre el progreso del usuario.

La navegación se realiza mediante una barra inferior.

### 5. Estadísticas

La pantalla de estadísticas muestra:

- Cantidad total de tareas.
- Cantidad de tareas completadas.
- Cantidad de tareas pendientes.
- Porcentaje de cumplimiento.
- Barra de progreso animada.

Los valores se calculan a partir de las tareas registradas y se actualizan cuando cambia su estado.

### 6. Recursos nativos

**Notificaciones:** al registrar una nueva tarea, la aplicación puede mostrar una notificación del sistema Android.

**Vibración:** al completar una tarea, la aplicación activa una breve vibración como confirmación de la acción.

Se utilizan los permisos correspondientes según las características y la versión de Android.

### 7. Animaciones

TaskGo incorpora animaciones para mejorar la respuesta visual de la interfaz, incluyendo:

- Animaciones en elementos de la lista.
- Transiciones de color según el estado de las tareas.
- Cambios visuales animados al completar actividades.
- Animación de la barra de progreso de estadísticas.

## Arquitectura del proyecto

La aplicación utiliza el patrón **MVVM (Model - View - ViewModel)** para separar la representación visual, el manejo del estado y la gestión de datos.

### Model

Representa la información de una tarea mediante sus atributos principales:

- Identificador.
- Título.
- Descripción.
- Estado de cumplimiento.

### View

Está implementada mediante Jetpack Compose y contiene las pantallas y componentes visuales con los que interactúa el usuario.

### ViewModel

Gestiona el estado de las tareas, coordina las operaciones de creación, actualización y eliminación, y proporciona los datos necesarios para actualizar la interfaz.

### Base de datos

La persistencia se implementa mediante Room, utilizando:

- `TareaEntity`: entidad de la base de datos.
- `TareaDao`: operaciones de acceso a los datos.
- `TaskGoDatabase`: configuración de la base de datos local.

## Estructura principal del proyecto

```text
TaskGo/
├── app/
│   └── src/main/
│       ├── java/com/example/taskgo/
│       │   ├── database/
│       │   │   ├── TareaEntity.kt
│       │   │   ├── TareaDao.kt
│       │   │   └── TaskGoDatabase.kt
│       │   ├── model/
│       │   │   └── Tarea.kt
│       │   ├── ui/
│       │   │   └── screens/
│       │   │       ├── AgregarTareaScreen.kt
│       │   │       └── EstadisticasScreen.kt
│       │   ├── viewmodel/
│       │   │   └── TareaViewModel.kt
│       │   ├── MainActivity.kt
│       │   └── RecursosNativos.kt
│       └── AndroidManifest.xml
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Requisitos para ejecutar

- Android Studio instalado.
- SDK de Android configurado.
- Conexión a Internet para descargar dependencias de Gradle.
- Emulador Android o dispositivo físico compatible.

## Instalación y ejecución

1. Descargar o clonar el repositorio de GitHub.
2. Si el proyecto se encuentra comprimido, extraer el archivo ZIP.
3. Abrir Android Studio.
4. Seleccionar **Open**.
5. Buscar la carpeta raíz del proyecto TaskGo.
6. Esperar a que finalice la sincronización de Gradle.
7. Configurar un emulador Android o conectar un dispositivo físico.
8. Presionar **Run** para compilar y ejecutar la aplicación.
9. Conceder el permiso de notificaciones si el dispositivo lo solicita.

## Pruebas funcionales

Para verificar el funcionamiento de TaskGo se pueden realizar las siguientes pruebas:

1. Crear una tarea con título y descripción válidos.
2. Intentar guardar una tarea con campos vacíos y comprobar los mensajes de error.
3. Editar una tarea existente.
4. Marcar una tarea como completada.
5. Comprobar la vibración al completar una tarea.
6. Crear una tarea y verificar la notificación.
7. Eliminar una tarea.
8. Consultar las estadísticas y verificar los valores.
9. Cerrar y volver a abrir la aplicación para comprobar que las tareas permanezcan guardadas.

## Herramientas de organización

**GitHub:** utilizado para almacenar el código fuente del proyecto y gestionar sus versiones.

**Trello:** herramienta contemplada para organizar las actividades del equipo, distribuir responsabilidades y registrar el avance de las tareas.

## Posibles mejoras futuras

- Incorporar categorías para clasificar tareas.
- Agregar fechas límite y prioridades.
- Implementar recordatorios programados.
- Incorporar filtros y búsqueda.
- Mejorar la accesibilidad y adaptación a diferentes tamaños de pantalla.
- Ampliar las pruebas automatizadas.
- Separar el acceso a datos mediante una capa Repository.

## Conclusión

El desarrollo de TaskGo permitió aplicar conocimientos fundamentales de programación móvil Android, integrando interfaces modernas, navegación funcional, gestión de estado y almacenamiento local.

La utilización de Kotlin y Jetpack Compose facilitó la construcción de componentes visuales reutilizables, mientras que la arquitectura MVVM permitió organizar el código y separar responsabilidades.

La implementación de Room proporciona persistencia de datos, y los recursos nativos, como notificaciones y vibración, mejoran la interacción del usuario con la aplicación.

Finalmente, las animaciones y estadísticas complementan la experiencia, proporcionando una solución funcional para la administración de tareas personales.

TaskGo constituye una base para continuar incorporando nuevas funcionalidades y perfeccionando el proyecto durante las siguientes etapas de desarrollo académico.
