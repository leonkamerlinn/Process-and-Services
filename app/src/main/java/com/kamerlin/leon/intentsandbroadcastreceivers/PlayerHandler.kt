package com.kamerlin.leon.intentsandbroadcastreceivers

import android.os.Handler
import android.os.Message
import android.widget.Toast

class PlayerHandler(private val service: ProcessPlayerService) : Handler() {

    override fun handleMessage(msg: Message) {
        println("hello world")
        when(msg.arg1) {
            MainActivity.ARG_PLAY -> service.play()
            MainActivity.ARG_PAUSE -> service.pause()
            MainActivity.ARG_IS_PLAY -> {
                val isPlaying = if (service.isPlaying()) 1 else 0
                val message = Message.obtain()
                message.arg1 = isPlaying
                if (msg.arg2 == 1) {
                    message.arg2 = 1
                }
                message.replyTo = service.messenger
                msg.replyTo.send(message)
            }
        }
    }
}
