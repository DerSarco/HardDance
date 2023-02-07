package com.example.harddance.ui

import androidx.lifecycle.*
import com.example.harddance.data.entities.TrackList
import com.example.harddance.data.entities.Tracks
import com.example.harddance.data.entities.UserData
import com.example.harddance.data.repository.HearthisRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: HearthisRepository
) :
    ViewModel() {

    private val _trackList: MutableLiveData<TrackList> = MutableLiveData()
    val trackList: LiveData<TrackList>
        get() = _trackList
    private val _userData: MutableLiveData<UserData> = MutableLiveData()
    val userData: LiveData<UserData>
        get() = _userData

    fun getFirstTrack(): Tracks? {
        return _trackList.value?.tracks?.get(0)
    }

    fun getTrackList() {
        viewModelScope.launch(Dispatchers.IO) {
            _trackList.postValue(repository.getTracks())
        }
    }

    fun getUserData() {
        viewModelScope.launch {
            _userData.postValue(repository.getUserInfo())
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val repository: HearthisRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        } else {
            throw java.lang.NullPointerException("No assignable class found.")
        }
    }
}