package com.sdpodcast.di

import com.sdpodcast.core.coment.appCoroutinesDispatcher
import com.sdpodcast.data.repo.channel.ChannelRepoImpl
import com.sdpodcast.presentation.ui.channel.ChannelListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModel = module {
    factory { ChannelRepoImpl() }
    viewModel { ChannelListViewModel(get<ChannelRepoImpl>(), appCoroutinesDispatcher()) }
}