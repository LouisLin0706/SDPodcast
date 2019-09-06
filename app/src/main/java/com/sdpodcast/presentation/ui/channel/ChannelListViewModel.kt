package com.sdpodcast.presentation.ui.channel

import androidx.lifecycle.MutableLiveData
import com.sdpodcast.core.arch.BaseViewModel
import com.sdpodcast.core.coment.CoroutinesDispatcherProvider
import com.sdpodcast.data.repo.Result
import com.sdpodcast.data.repo.channel.ChannelRepo
import com.sdpodcast.data.repo.channel.model.Channel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ChannelListViewModel(
    val channelRepo: ChannelRepo,
    override val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) :
    BaseViewModel(coroutinesDispatcherProvider) {

    val channelDataChange = MutableLiveData<Channel>()

    fun loadChannel(id: Int) {
        GlobalScope.launch(coroutineContext) {
            val data = channelRepo.getChannelPlaylist(id)
            when (data) {
                is Result.OnSuccess -> {
                    channelDataChange.postValue(data.data)
                }
                is Result.OnError -> {
                    //TODO error
                }
            }
        }
    }
}