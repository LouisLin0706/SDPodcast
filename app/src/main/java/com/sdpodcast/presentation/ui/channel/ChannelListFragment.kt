package com.sdpodcast.presentation.ui.channel

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdpodcast.R
import com.sdpodcast.core.arch.BaseFragment
import com.sdpodcast.data.repo.channel.model.ChannelTrack
import kotlinx.android.synthetic.main.fragment_channel_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChannelListFragment : BaseFragment() {

    companion object {
        //TODO Input value : channel id
        fun newInstance(): ChannelListFragment {
            return ChannelListFragment()
        }
    }


    private val channelListViewModel: ChannelListViewModel by viewModel()

    override fun getLayoutId(): Int {
        return R.layout.fragment_channel_list
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val channelListAdapter = ChannelListAdapter()
        val linearLayoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = channelListAdapter
        }
        channelListViewModel.channelDataChange.observe(this, Observer { channel ->
            textView_channel_title.text = channel.title
            textView_channel_desc.text = channel.desc
            channelListAdapter.swapData(channel.channelTracks)
        })
        channelListViewModel.loadChannel(123)
        channelListAdapter.onItemClickListener = object : ChannelListAdapter.OnItemClickListener {
            override fun onItemClick(track: ChannelTrack) {
                //TODO PLAY MUSIC
            }

        }
    }
}