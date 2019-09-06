package com.sdpodcast.data.repo.channel

import com.sdpodcast.data.network.ChannelService
import com.sdpodcast.data.repo.Result
import com.sdpodcast.data.repo.channel.model.Channel
import com.sdpodcast.data.repo.channel.model.ChannelTrack
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChannelRepoImpl : ChannelRepo {

    private var channelService: ChannelService

    init {
        //TODO SHOULD BE DI From outstide & SEPRATE use interface
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.podcast.de")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        channelService = retrofit.create(ChannelService::class.java)
    }

    override suspend fun getChannelPlaylist(id: Int): Result<Channel> {
        try {
            val channelInfo = channelService.fetchChannelInfo(id)
            val tracks = channelInfo.channel.episodes.map {
                ChannelTrack(
                    id = it.id,
                    title = it.title,
                    url = "http://www.hochmuth.com/mp3/Haydn_Cello_Concerto_D-1.mp3"
                )
            }.toList()
            val channel = Channel(
                title = channelInfo.channel.channelTitle,
                desc = channelInfo.channel.description,
                channelTracks = tracks,
                coverPhoto = ""
            )
            return Result.OnSuccess(channel)
        } catch (e: Exception) {
            return Result.OnError(com.sdpodcast.data.repo.Error("error", e))
        }
    }
}