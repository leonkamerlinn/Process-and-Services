package com.kamerlin.leon.intentsandbroadcastreceivers

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.AppCompatButton

class MainActivity : AppCompatActivity() {

    val rootLayout by lazy { findViewById<CoordinatorLayout>(R.id.rootLayout) }
    val playButton by lazy { findViewById<AppCompatButton>(R.id.play) }
    val downloadButton by lazy { findViewById<AppCompatButton>(R.id.download) }
    lateinit var playerService: PlayerService

    var serviceBound = false


    val serviceConnection = object: ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            serviceBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            serviceBound = true
            val localbinder: PlayerService.LocalBinder = service as PlayerService.LocalBinder
            playerService = localbinder.service
            if (playerService.isPlaying()) {
                playButton.text = "Pause"
            }
        }
    }





    val activityMessenger by lazy { Messenger(ActivityHandler(this)) }
    lateinit var processServiceMessenger: Messenger


    val processServiceConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            serviceBound = true
            processServiceMessenger = Messenger(service)
            val message = Message.obtain()
            message.arg1 = ARG_IS_PLAY
            message.arg2 = 1
            message.replyTo = activityMessenger
            processServiceMessenger.send(message)

        }

        override fun onServiceDisconnected(name: ComponentName?) {
            serviceBound = false
        }
    }


    companion object {
        val EXTRA_SONG = "extra_song"
        val ARG_PLAY = 0
        val ARG_PAUSE = 1
        val ARG_IS_PLAY = 2
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        playButton.setOnClickListener {
            //playMusicInService()
            playMusicProcessService()
        }

        downloadButton.setOnClickListener {
            Playlist.songs.forEach {
                val intent = Intent(this@MainActivity, DownloadIntentService::class.java)
                intent.putExtra(EXTRA_SONG, it)
                startService(intent)
            }
        }
    }

    fun playMusicProcessService() {
        if (serviceBound) {
            val intent = Intent(this@MainActivity, ProcessPlayerService::class.java)
            intent.putExtra(EXTRA_SONG, Playlist.songs[0])
            startService(intent)
            val message = Message.obtain()
            message.arg1 = 2
            message.replyTo = activityMessenger
            processServiceMessenger.send(message)

        }
    }

    fun playMusicInService() {
        if (serviceBound) {

            if (playerService.isPlaying()) {
                playerService.pause()
                playButton.text = "Play"
            } else {
                val intent = Intent(this, PlayerService::class.java)
                startService(intent)
                playerService.play()
                playButton.text = "Pause"
            }

        }
    }


    override fun onStart() {
        super.onStart()
        /*val intent = Intent(this, PlayerService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)*/
        val intent = Intent(this, ProcessPlayerService::class.java)
        bindService(intent, processServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (serviceBound) {
            //unbindService(serviceConnection)
            unbindService(processServiceConnection)
            serviceBound = false
        }
    }

    fun changePlayButtonText(s: String) {
        playButton.text = s
    }

}
