package com.sdpodcast.data.repo

sealed class Result<out T> {
    data class OnSuccess<out T>(val data: T) : Result<T>()
    data class OnError<out T>(val error: Error) : Result<T>()
}

open class Error(
    val message: String,
    val cause: Throwable
)


class PlaylistFetchError(message: String? = null, cause: Throwable) : Error(message ?: "Fetch Playlist Fail", cause)