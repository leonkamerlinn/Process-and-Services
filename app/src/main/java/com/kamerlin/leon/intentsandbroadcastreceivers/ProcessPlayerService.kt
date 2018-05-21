package com.kamerlin.leon.intentsandbroadcastreceivers

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.os.Messenger
import android.support.v4.app.NotificationCompat
import com.kamerlin.leon.intentsandbroadcastreceivers.models.Song

class ProcessPlayerService: Service() {
    private val REQUEST_OPEN = 99
    val NOTIFICATION_CHANEL_ID = "chanel_id"
    val player by lazy { MediaPlayer.create(this, R.raw.jingle) }
    val messenger by lazy { Messenger(PlayerHandler(this)) }


    override fun onBind(intent: Intent?): IBinder {
        println("onBind")
        return messenger.binder
    }



    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        if (intent.hasExtra(MainActivity.EXTRA_SONG)) {
            val song = intent.getParcelableExtra<Song>(MainActivity.EXTRA_SONG)
            val mainIntent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, REQUEST_OPEN, mainIntent, 0)

            val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANEL_ID)
                    .setSmallIcon(R.drawable.ic_queue_music_white)
                    .setContentTitle(song.title)
                    .setContentText(song.artist)
                    .setContentIntent(pendingIntent)

            val notification = notificationBuilder.build()
            startForeground(11, notification)
        }

        player.setOnCompletionListener {
            stopSelf()
        }
        return Service.START_NOT_STICKY
    }

    override fun onDestroy() {
        player.release()
    }

    fun play() {
        player.start()
    }

    fun pause() {
        player.pause()
    }

    fun isPlaying(): Boolean {
        return player.isPlaying
    }


}