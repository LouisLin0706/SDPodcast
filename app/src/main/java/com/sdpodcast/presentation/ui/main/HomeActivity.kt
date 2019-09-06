package com.sdpodcast.presentation.ui.main

import android.os.Bundle
import com.sdpodcast.R
import com.sdpodcast.core.arch.BaseActivity
import com.sdpodcast.presentation.ui.channel.ChannelListFragment

class HomeActivity : BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.activity_home
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                ChannelListFragment.newInstance(),
                ChannelListFragment::class.java.simpleName
            ).commit()
    }
}