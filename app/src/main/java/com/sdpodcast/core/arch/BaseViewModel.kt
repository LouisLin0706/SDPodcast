package com.sdpodcast.core.arch

import androidx.lifecycle.ViewModel
import com.sdpodcast.core.coment.CoroutinesDispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(open val coroutinesDispatcherProvider: CoroutinesDispatcherProvider) :
    ViewModel(),
    CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + coroutinesDispatcherProvider.io


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}