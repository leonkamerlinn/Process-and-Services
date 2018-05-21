package com.kamerlin.leon.intentsandbroadcastreceivers

import android.os.Handler
import android.os.Message
import com.kamerlin.leon.intentsandbroadcastreceivers.models.Song

class DownloadHandler: Handler() {
    var service: DownloadService? = null

    companion object {
        val TAG = DownloadHandler::class.java.simpleName!!
    }
    override fun handleMessage(msg: Message) {
        val song = msg.obj as Song
        downloadSong(song.title)
        service?.stopSelf(msg.arg1)
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

        println("$song: downloaded!")
    }
}