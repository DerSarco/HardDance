package com.example.harddance.di

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.example.harddance.player.service.MusicService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioManagerProvider @Inject constructor(@ApplicationContext private val context: Context) {
    private var musicService: MusicService? = null
    private var bounded = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            bounded = true
            val binder = service as MusicService.MusicBinder
            musicService = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
            bounded = false
        }
    }

    fun getAudioService(): MusicService? {
        return if (this.bounded) {
            this.musicService
        } else {
            null
        }
    }

    init {
        Intent(context, MusicService::class.java).also { intent ->
            context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }
}