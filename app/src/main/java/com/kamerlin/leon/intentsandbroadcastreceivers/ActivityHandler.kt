package com.kamerlin.leon.intentsandbroadcastreceivers

import android.os.Handler
import android.os.Message


class ActivityHandler(val mainActivity: MainActivity): Handler() {
    override fun handleMessage(msg: Message) {
        println("$msg activity handler")
        if (msg.arg1 == MainActivity.ARG_PLAY) {
            // Music is NOT playing
            if (msg.arg2 == 1) {
                mainActivity.changePlayButtonText("Play")
            } else {
                // Play the music
                val message = Message.obtain()
                message.arg1 = 0
                msg.replyTo.send(message)


                // Change play Button to say "Pause"
                mainActivity.changePlayButtonText("Pause")
            }
        } else if (msg.arg1 == MainActivity.ARG_PAUSE) {
            // Music is playing
            if (msg.arg2 == 1) {
                mainActivity.changePlayButtonText("Pause")
            } else {
                // Pause the music
                val message = Message.obtain()
                message.arg1 = 1
                msg.replyTo.send(message)



                // Change play Button to say "Play"
                mainActivity.changePlayButtonText("Play")
            }
        }
    }
}