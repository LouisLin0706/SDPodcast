package com.sdpodcast.data.network

import com.google.gson.annotations.SerializedName

data class ChannelInfo(val channel: ChannelRaw)

data class ChannelRaw(
    @SerializedName("channel_title") val channelTitle: String,
    @SerializedName("description") val description: String,
    @SerializedName("episodes") val episodes: List<Episodes>
)

data class Episodes(
    @SerializedName("show_id") val id: Int,
    val title: String,
    @SerializedName("media_link") val trackUrl: String
)