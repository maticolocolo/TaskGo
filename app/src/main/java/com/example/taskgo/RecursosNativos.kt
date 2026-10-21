
package com.example.taskgo

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

object RecursosNativos {

    private const val CANAL_ID = "taskgo_tareas"

    @Suppress("DEPRECATION")
    fun vibrar(context: Context) {

        val vibrator: Vibrator =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val manager = context.getSystemService(
                    Context.VIBRATOR_MANAGER_SERVICE
                ) as VibratorManager

                manager.defaultVibrator
            } else {
                context.getSystemService(
                    Context.VIBRATOR_SERVICE
                ) as Vibrator
            }

        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        120L,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(120L)
            }
        }
    }

    fun crearCanalNotificaciones(context: Context) {

        val canal = NotificationChannel(
            CANAL_ID,
            "Tareas de TaskGo",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Notificaciones de tareas creadas"
        }

        val manager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        manager.createNotificationChannel(canal)
    }

    @SuppressLint("MissingPermission")
    fun notificarTareaCreada(
        context: Context,
        titulo: String
    ) {

        crearCanalNotificaciones(context)

        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val notificacion = NotificationCompat.Builder(
            context,
            CANAL_ID
        )
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("TaskGo")
            .setContentText("Nueva tarea creada: $titulo")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(
            System.currentTimeMillis().toInt(),
            notificacion
        )
    }
}
