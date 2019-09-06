package com.sdpodcast.presentation.ui.channel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sdpodcast.R
import com.sdpodcast.data.repo.channel.model.ChannelTrack
import kotlinx.android.synthetic.main.adapter_channel_list_item.view.*

/**
 * TODO Paging handle
 */
class ChannelListAdapter : RecyclerView.Adapter<ChannelListAdapter.ChannelListItemViewHolder>() {


    interface OnItemClickListener {
        fun onItemClick(track: ChannelTrack)
    }

    public var onItemClickListener: OnItemClickListener? = null
    private var channelTracks: List<ChannelTrack> = mutableListOf()

    fun swapData(channelTracks: List<ChannelTrack>) {
        this.channelTracks = channelTracks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelListItemViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_channel_list_item, parent, false)
        return ChannelListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return channelTracks.size
    }

    override fun onBindViewHolder(holder: ChannelListItemViewHolder, position: Int) {
        holder.bindView(channelTracks[position])
    }


    inner class ChannelListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(channelTrack: ChannelTrack) {
            itemView.textView_title.text = channelTrack.title
            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(channelTrack)
            }
        }
    }
}