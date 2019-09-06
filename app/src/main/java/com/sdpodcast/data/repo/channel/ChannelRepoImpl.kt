package com.sdpodcast.data.repo.channel

import com.sdpodcast.data.repo.Result
import com.sdpodcast.data.repo.channel.model.Channel

class ChannelRepoImpl : ChannelRepo {
    override suspend fun getChannelPlaylist(id: Int): Result<Channel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}