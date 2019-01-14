package a.benri.notifications

import android.annotation.SuppressLint
import android.app.Notification
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build


class MyFirebaseMessagingService : FirebaseMessagingService() {
    val TAG = "FirebaseMessage"

    override fun onNewToken(token: String?) {
        Log.d(TAG, "$token")
    }

    //Se recoge el mensaje
    @SuppressLint("LongLogTag")
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "Hola: ${remoteMessage.from}")
        val mNotificationID = 101
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        mNotificationManager.notify(mNotificationID, notificationIntent(remoteMessage))

    }

    //Se crea la notificacion
    private fun defaultNotification(remoteMessage: RemoteMessage) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Notification.Builder(this, NotificationUtils.CHANNEL_ID)
    }
    else {
        Notification.Builder(this)
    }.apply {
        setContentTitle(remoteMessage.notification?.title)
        setContentText(remoteMessage.notification?.body)
        setSmallIcon(android.R.drawable.ic_dialog_info)
    }

    //Se crea el Intent que muestra la notificación
    private fun notificationIntent(remoteMessage: RemoteMessage) = PendingIntent.getActivity(this,
        0,
        Intent(this, MainActivity::class.java),
        PendingIntent.FLAG_UPDATE_CURRENT).run {
        defaultNotification(remoteMessage).setContentIntent(this).build()
    }

}