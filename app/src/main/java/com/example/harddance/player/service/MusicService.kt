package com.example.harddance.player.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.harddance.di.ExoPlayerProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MusicService : Service() {

    companion object {
        const val CHANNEL_ID = "CHANNEL_ID"
        const val NOTIFICATION_ID = 1
        const val MEDIA_SESSION_TAG = "PLAYER_MEDIA_PODCAST"
    }

    @Inject
    lateinit var player: ExoPlayerProvider
    private var notificationManager: HDANotificationManager? = null

    override fun onCreate() {
        super.onCreate()
        notificationManager = HDANotificationManager(this.applicationContext)
        notificationManager?.initializeNotification()
    }

    fun createNotification(){
        notificationManager?.showNotification(player.getPlayer())
    }

    fun disposeNotification(){
        notificationManager?.disposeNotification()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onDestroy() {
        stopSelf()
        releasePlayer()
        super.onDestroy()
    }


    private fun releasePlayer() {
        TODO("Not yet implemented")
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return false
    }

    private val binder = MusicBinder()

    inner class MusicBinder : Binder() {
        fun getService() = this@MusicService
    }

    fun play() {

    }

    fun pause() = player.pause()

}

