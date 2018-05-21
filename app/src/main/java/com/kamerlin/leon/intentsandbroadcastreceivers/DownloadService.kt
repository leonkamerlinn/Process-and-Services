package com.kamerlin.leon.intentsandbroadcastreceivers

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Message
import com.kamerlin.leon.intentsandbroadcastreceivers.models.Song

class DownloadService: Service() {

    lateinit var downloadThread: DownloadThread

    override fun onCreate() {
        downloadThread = DownloadThread()
        downloadThread.name = "DownloadThread"
        downloadThread.start()

        while (downloadThread.handler == null) {}

        downloadThread.handler!!.service = this

        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val song: Song = intent.getParcelableExtra(MainActivity.EXTRA_SONG)

        val message = Message.obtain()
        message.obj = song
        message.arg1 = startId
        downloadThread.handler!!.sendMessage(message)


        return Service.START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent?): IBinder? = null
}