package com.example.harddance.data.network

import com.example.harddance.data.network.entities.TrackNetwork
import com.example.harddance.data.network.entities.User
import retrofit2.http.GET

interface HearthisAPI {
    @GET("/hardanceattack/?type=tracks&page=1&count=100")
    suspend fun getTrackList(): TrackNetwork

    @GET("/hardanceattack/")
    suspend fun getUserData(): User
}