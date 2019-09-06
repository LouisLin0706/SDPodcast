package com.sdpodcast.lib_player.error

object Logger : ErrorLogger {

    private var outer: ErrorLogger? = null

    fun setErrorLogger(errorLogger: ErrorLogger) {
        outer = errorLogger
    }

    override fun onError(t: Throwable) {
        t.printStackTrace()
        outer?.onError(t)
    }
}