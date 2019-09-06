package com.sdpodcast.lib_player.core

import com.sdpodcast.lib_player.track.Track

data class PlayerInfo(
    var isLoading: Boolean = false,
    var isPlaying: Boolean = false,
    var currentTrack: Track? = null
)