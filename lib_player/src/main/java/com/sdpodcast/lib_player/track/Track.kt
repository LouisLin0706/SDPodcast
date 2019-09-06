package com.sdpodcast.lib_player.track

import android.content.Context
import com.google.android.exoplayer2.source.MediaSource

abstract class Track(
    open val id: Int,
    open val title: String
) {
    abstract fun transform(context: Context): MediaSource
}