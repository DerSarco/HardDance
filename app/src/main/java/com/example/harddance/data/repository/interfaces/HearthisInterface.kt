package com.example.harddance.data.repository.interfaces

import com.example.harddance.data.entities.TrackList
import com.example.harddance.data.entities.UserData

interface HearthisInterface {
    suspend fun getTracks(): TrackList
    suspend fun getUserInfo(): UserData

}