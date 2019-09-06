package com.sdpodcast.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface ChannelService {

    @GET("/channel/{channelId}.json")
    suspend fun fetchChannelInfo(@Path("channelId") channelId: Int): ChannelInfo
}