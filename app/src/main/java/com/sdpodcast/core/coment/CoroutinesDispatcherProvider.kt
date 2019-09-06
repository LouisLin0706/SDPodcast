package com.sdpodcast.core.coment

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

data class CoroutinesDispatcherProvider(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val computation: CoroutineDispatcher
)

fun appCoroutinesDispatcher(): CoroutinesDispatcherProvider {
    return CoroutinesDispatcherProvider(
        Dispatchers.Main,
        Dispatchers.Unconfined,
        Dispatchers.IO
    )
}

fun testCoroutinesDispatcher(): CoroutinesDispatcherProvider =
    CoroutinesDispatcherProvider(
        Dispatchers.Main,
        Dispatchers.Main,
        Dispatchers.Main
    )

