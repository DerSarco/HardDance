package com.example.harddance.player.service

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class MusicConnector @Inject constructor(context: Context) {

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean> = _isConnected


    private val mediaBrowserConnectionCallback = MediaBrowserConnectionCallback(context)

    // TODO: Ver el mótivo de que mediaController no es asignado
    private val mediaBrowser = MediaBrowserCompat(
        context, ComponentName(
            context, MusicService::class.java
        ), mediaBrowserConnectionCallback, null
    ).apply {
        connect()
    }

    lateinit var mediaController: MediaControllerCompat

    val transportControls: MediaControllerCompat.TransportControls
        get() = mediaController.transportControls


    fun subscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.subscribe(parentId, callback)
    }

    fun unsubscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.unsubscribe(parentId, callback)
    }


    private inner class MediaBrowserConnectionCallback(
        private val context: Context
    ) : MediaBrowserCompat.ConnectionCallback() {

        override fun onConnected() {
            Log.d("MusicServiceConnection", "CONNECTED")
            super.onConnected()
            _isConnected.postValue(true)
            mediaController = MediaControllerCompat(context, mediaBrowser.sessionToken).apply {
                registerCallback(MediaControllerCallback())
            }
        }

        override fun onConnectionSuspended() {
            super.onConnectionSuspended()
            Log.d("MusicServiceConnection", "SUSPENDED")

            _isConnected.postValue(
                false
            )
        }

        override fun onConnectionFailed() {
            super.onConnectionFailed()
            Log.d("MusicServiceConnection", "FAILED")
            _isConnected.postValue(
                false
            )
        }
    }


    private inner class MediaControllerCallback : MediaControllerCompat.Callback() {

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            // TODO:
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            // TODO:
        }

        override fun onSessionEvent(event: String?, extras: Bundle?) {
            super.onSessionEvent(event, extras)
            // TODO:
        }

        override fun onSessionDestroyed() {
            mediaBrowserConnectionCallback.onConnectionSuspended()
        }
    }
}