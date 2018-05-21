package com.kamerlin.leon.intentsandbroadcastreceivers

import android.app.IntentService
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.widget.Toast
import com.kamerlin.leon.intentsandbroadcastreceivers.models.Song

class DownloadIntentService : IntentService(DownloadIntentService::class.java.simpleName) {


    private val NOTIFICATION_ID = 22

    val notificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }



    init {
        setIntentRedelivery(true)
    }


    override fun onHandleIntent(intent: Intent) {
        val song: Song = intent.getParcelableExtra(MainActivity.EXTRA_SONG)

        val builder = NotificationCompat.Builder(this, "chanel_id")
                .setSmallIcon(R.drawable.ic_queue_music_white)
                .setContentTitle("Downloading")
                .setContentText(song.title)
                .setProgress(0, 0, true)


        notificationManager.notify(NOTIFICATION_ID, builder.build())
        downloadSong(song.title)
    }



    private fun downloadSong(song: String) {
        val endTime = System.currentTimeMillis() + 5 * 1000
        while (System.currentTimeMillis() < endTime) {
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }
        notificationManager.cancel(NOTIFICATION_ID)
        println("$song: downloaded!")
    }
}