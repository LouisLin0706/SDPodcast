package com.sdpodcast.lib_player.core

import com.sdpodcast.lib_player.track.Track

interface SDPlayer {
    fun play(track: Track, positionInMillis: Long = 0L, playWhenReady: Boolean = true)

    fun pause()

    fun resume()

    fun release()

    fun stop()

    fun getPlayerInfo(): PlayerInfo

    fun addTrackListener(trackListener: SDPlayerImpl.TrackListener)

    fun removeTrackListener(trackListener: SDPlayerImpl.TrackListener)
}