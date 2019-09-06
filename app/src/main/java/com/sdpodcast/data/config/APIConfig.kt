package com.sdpodcast.data.config


class APIConfig {

    companion object {
        const val PATH_CHANNEL = "/channel/{${APIConfig.KEY_CHANNEL_ID}}.json"
        const val KEY_CHANNEL_ID = "channelId"
    }
}