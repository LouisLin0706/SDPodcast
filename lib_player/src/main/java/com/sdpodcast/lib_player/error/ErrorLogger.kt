package com.sdpodcast.lib_player.error

interface ErrorLogger {
    fun onError(t: Throwable)
}