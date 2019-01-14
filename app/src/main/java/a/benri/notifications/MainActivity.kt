package a.benri.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.widget.Button

class MainActivity : AppCompatActivity() {
    //Notificaciones en local
    var CHANNEL_ID: String = "channel id"
    var notificationId: Int = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Util para API >= 26
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationUtils(this)
        }
        createNotificationChannel()
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            with(NotificationManagerCompat.from(this)) {
                // el id es único para cada notificación
                notify(notificationId, mBuilder.build())
            }
        }
    }

    var mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
        .setSmallIcon(R.drawable.notification_template_icon_bg)
        .setContentTitle("My notification")
        .setContentText("Here we have our new Notification ;)")
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("Here we have our new Notification ;)")
        )
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Registro del channel
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }



}