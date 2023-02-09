package com.example.harddance.ui

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import androidx.lifecycle.*
import com.example.harddance.data.entities.TrackList
import com.example.harddance.data.entities.Tracks
import com.example.harddance.data.entities.UserData
import com.example.harddance.data.repository.HearthisRepository
import com.example.harddance.player.service.MusicConnector
import com.example.harddance.player.service.MusicService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: HearthisRepository,
    private val musicConnector: MusicConnector
) :
    ViewModel() {

    //TODO: add transportControls to update the current status of the player.
    private val _trackList: MutableLiveData<TrackList> = MutableLiveData()
    val trackList: LiveData<TrackList>
        get() = _trackList
    private val _userData: MutableLiveData<UserData> = MutableLiveData()
    val userData: LiveData<UserData>
        get() = _userData

    init {
        musicConnector.subscribe(MusicService.CHANNEL_ID, object :
            MediaBrowserCompat.SubscriptionCallback() {
            override fun onChildrenLoaded(
                parentId: String,
                children: MutableList<MediaBrowserCompat.MediaItem>
            ) {
                super.onChildrenLoaded(parentId, children)
                val items = children.map {
                    Tracks(
                        it.description.iconUri.toString(),
                        it.description.title.toString(),
                        it.description.mediaUri.toString(),
                    )
                }
            }
        })
    }

    fun getFirstTrack(): Tracks? {
        return _trackList.value?.tracks?.get(0)
    }

    fun getTrackList() {
        viewModelScope.launch(Dispatchers.IO) {
            _trackList.postValue(repository.getTracks())
        }
    }

    fun play() {
        musicConnector.transportControls.play()
    }

    fun getUserData() {
        viewModelScope.launch {
            _userData.postValue(repository.getUserInfo())
        }
    }
}
