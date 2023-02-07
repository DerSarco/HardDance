package com.example.harddance.player.exo

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.example.harddance.player.service.HDANotificationManager
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.StyledPlayerView
import dagger.hilt.android.AndroidEntryPoint

class ExoPlayerInstance(private val context: Context) {

    private var player: ExoPlayer? = null
    private var playing = false
    private lateinit var onStop: () -> Unit

    fun getExoPlayer(): ExoPlayer? = player

   private fun createPlayer(): ExoPlayer {
        val player =
            ExoPlayer.Builder(
                context,
                DefaultRenderersFactory(context)
            ).build()
        val audioAttributes = AudioAttributes.Builder().run {
            setUsage(C.USAGE_MEDIA)
            setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            build()
        }
        player.addMediaItem(MediaItem.fromUri("https://hearthis.app/hardanceattack/21-hardance-attack-el-ultimo-el-primero-feat-mc-mike/listen/?s=5np"))

        player.setAudioAttributes(audioAttributes, true)
        return player
    }

    fun initializePlayer() {
        if (this.player == null) {
            player = createPlayer()
            this.play()
        }
        try {
            //todo: entregar music
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun play() {
        if (playing) {
            player?.playWhenReady = true
        }
    }

    fun pause() {
        player?.playWhenReady = false
    }

    fun stop() {
        player?.stop()
        player?.release()
        onStop()
    }
}