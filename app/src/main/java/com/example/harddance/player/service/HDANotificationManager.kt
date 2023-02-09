package com.example.harddance.player.service

import android.content.Context
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import com.example.harddance.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class HDANotificationManager(
    private val context: Context
) {

    private var notificationManager: PlayerNotificationManager? = null
    private lateinit var mediaSession: MediaSessionCompat


    fun initializeNotification() {
        this.mediaSession = MediaSessionCompat(context, MusicService.MEDIA_SESSION_TAG).apply {
            isActive = true
        }

        this.notificationManager =
            PlayerNotificationManager.Builder(
                context,
                MusicService.NOTIFICATION_ID,
                MusicService.CHANNEL_ID
            )
                .setChannelNameResourceId(R.string.notification_channel_string)
                .setChannelDescriptionResourceId(R.string.notification_channel_description)
                .build()

        this.notificationManager?.apply {
            setMediaSessionToken(mediaSession.sessionToken)
            setColorized(true)
            setUseChronometer(true)
            setSmallIcon(R.drawable.ic_notification)
            setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
            setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
            setPriority(NotificationCompat.PRIORITY_LOW)
            setUsePlayPauseActions(true)
            setUseStopAction(true)
        }
    }

    fun showNotification(exoPlayer: ExoPlayer) {
        this.notificationManager?.setPlayer(exoPlayer)
    }

    fun disposeNotification() {
        this.notificationManager?.setPlayer(null)
    }
}