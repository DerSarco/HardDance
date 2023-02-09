package com.example.harddance.di

import android.content.Context
import com.example.harddance.player.exo.ExoPlayerInstance
import com.google.android.exoplayer2.ExoPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExoPlayerProvider @Inject constructor(@ApplicationContext private val context: Context) {
    val player = ExoPlayerInstance(context)

    init {
        player.initializePlayer()
    }

    fun getPlayer(): ExoPlayer  = player.getExoPlayer()!!

    fun play() = player.play()
    fun pause() = player.pause()
}