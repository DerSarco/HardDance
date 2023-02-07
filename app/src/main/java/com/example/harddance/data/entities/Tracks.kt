package com.example.harddance.data.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tracks(
    val cover: String,
    val name: String,
    val url: String
): Parcelable
