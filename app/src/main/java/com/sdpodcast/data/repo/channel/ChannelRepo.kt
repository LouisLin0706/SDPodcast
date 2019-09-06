package com.sdpodcast.data.repo.channel

import com.sdpodcast.data.repo.Result
import com.sdpodcast.data.repo.channel.model.Channel

interface ChannelRepo {
    //TODO PAGING
    suspend fun getChannelPlaylist(id: Int): Result<Channel>
}