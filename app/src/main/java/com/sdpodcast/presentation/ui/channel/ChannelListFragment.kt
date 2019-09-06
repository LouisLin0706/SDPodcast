package com.sdpodcast.presentation.ui.channel

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdpodcast.R
import com.sdpodcast.core.arch.BaseFragment
import com.sdpodcast.data.repo.channel.model.ChannelTrack
import com.sdpodcast.lib_player.core.SDPlayer
import com.sdpodcast.lib_player.core.SDPlayerFactory
import com.sdpodcast.lib_player.core.SDPlayerImpl
import com.sdpodcast.lib_player.track.Track
import kotlinx.android.synthetic.main.fragment_channel_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChannelListFragment : BaseFragment() {

    private var sdPlayer: SDPlayer? = null

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
        sdPlayer = SDPlayerFactory.newInstance(context!!)
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
        channelListViewModel.loadChannel(14677)
        channelListAdapter.onItemClickListener = object : ChannelListAdapter.OnItemClickListener {
            override fun onItemClick(track: ChannelTrack) {
                sdPlayer?.play(track)
            }
        }
        button_play_pause.setOnClickListener {

            if (sdPlayer?.getPlayerInfo()?.isPlaying == true) {
                sdPlayer?.pause()
            } else {
                sdPlayer?.resume()
            }

        }

        sdPlayer?.addTrackListener(
            object : SDPlayerImpl.SimpleTrackListener() {

                override fun onTrackReady(track: Track) {
                    textView_podcast_title.text = track.title
                    if (sdPlayer?.getPlayerInfo()?.isPlaying == true) {
                        button_play_pause.setImageResource(R.drawable.ic_pause_black_24dp)
                    } else {
                        button_play_pause.setImageResource(R.drawable.ic_play_arrow_black_24dp)
                    }
                }

                override fun onTrackPreparing(track: Track) {
                }
            })
    }

    override fun onPause() {
        super.onPause()
        sdPlayer?.pause()
    }

    override fun onResume() {
        super.onResume()
        sdPlayer?.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        sdPlayer?.release()
    }
}