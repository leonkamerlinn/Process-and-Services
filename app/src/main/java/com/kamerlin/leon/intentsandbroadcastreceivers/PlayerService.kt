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

class PlayerService: Service() {
    val player by lazy { MediaPlayer.create(this, R.raw.jingle) }
    val localBinder by lazy { LocalBinder() }

    override fun onBind(intent: Intent?): IBinder = localBinder



    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

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

    inner class LocalBinder : Binder() {
        val service: PlayerService
            get() = this@PlayerService
    }
}