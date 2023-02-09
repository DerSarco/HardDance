package com.example.harddance.data.repository

import com.example.harddance.data.entities.TrackList
import com.example.harddance.data.entities.Tracks
import com.example.harddance.data.entities.UserData
import com.example.harddance.data.network.NetworkService
import com.example.harddance.data.network.entities.TrackNetwork
import com.example.harddance.data.network.entities.User
import com.example.harddance.data.repository.interfaces.HearthisInterface

class HearthisRepository : HearthisInterface {
    override suspend fun getTracks(): TrackList {
        return mapTrackEntity(NetworkService.heartThisService.getTrackList())
    }
    override suspend fun getUserInfo(): UserData {
        return mapUserEntity(NetworkService.heartThisService.getUserData())
    }
    private fun mapTrackEntity(tracks: TrackNetwork): TrackList {
        val list = arrayListOf<Tracks>()
        tracks.forEach {
            list.add(
                Tracks(
                    cover = it.artwork_url,
                    name = it.title,
                    url = it.stream_url
                )
            )
        }
        return TrackList(list)
    }

    private fun mapUserEntity(user: User): UserData {
        return UserData(
            avatar = user.avatar_url
        )
    }
}