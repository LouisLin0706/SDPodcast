package com.sdpodcast.lib_player.core

import android.content.Context
import android.os.Looper
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.LoadControl
import com.google.android.exoplayer2.RenderersFactory
import com.google.android.exoplayer2.analytics.AnalyticsCollector
import com.google.android.exoplayer2.drm.DrmSessionManager
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.util.Util

class SDPlayerFactory {
    companion object {

        fun newInstance(
            context: Context,
            renderersFactory: RenderersFactory = DefaultRenderersFactory(context),
            trackSelector: TrackSelector = DefaultTrackSelector(),
            loadControl: LoadControl = DefaultLoadControl(),
            drmSessionManager: DrmSessionManager<FrameworkMediaCrypto>? = null,
            bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter.Builder(context).build(),
            analyticsCollectorFactory: AnalyticsCollector.Factory = AnalyticsCollector.Factory(),
            looper: Looper = Util.getLooper()
        ): SDPlayer {
            return SDPlayerImpl(
                context,
                renderersFactory,
                trackSelector,
                loadControl,
                drmSessionManager,
                bandwidthMeter,
                analyticsCollectorFactory,
                looper
            )
        }
    }
}