package com.sdpodcast.data.repo.channel.model

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.sdpodcast.lib_player.core.DefaultMediaSourceCreator
import com.sdpodcast.lib_player.track.Track

data class ChannelTrack(
    override val id: Int,
    val url: String,
    override val title: String
) : Track(id, title) {

    override fun transform(context: Context): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(context, "Soda Labs")
        return DefaultMediaSourceCreator.createMediaSourceFrom(
            dataSourceFactory,
            uri = Uri.parse(url)
        )
    }
}

data class Channel(
    val title: String,
    val coverPhoto: String,
    val desc: String,
    val channelTracks: List<ChannelTrack>
)