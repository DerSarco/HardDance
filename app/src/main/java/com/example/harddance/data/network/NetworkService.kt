package com.example.harddance.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api-v2.hearthis.at/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val heartThisService = retrofit.create(HearthisAPI::class.java)

}