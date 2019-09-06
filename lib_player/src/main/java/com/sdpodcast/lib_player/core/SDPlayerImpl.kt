package com.sdpodcast.lib_player.core

import android.content.Context
import android.os.Looper
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.analytics.AnalyticsCollector
import com.google.android.exoplayer2.drm.DrmSessionManager
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.sdpodcast.lib_player.error.Logger
import com.sdpodcast.lib_player.track.Track
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.CopyOnWriteArraySet

class SDPlayerImpl(
    private val context: Context,
    private val renderersFactory: RenderersFactory,
    private val trackSelector: TrackSelector,
    private val loadControl: LoadControl,
    private val drmSessionManager: DrmSessionManager<FrameworkMediaCrypto>?,
    private val bandwidthMeter: BandwidthMeter,
    private val analyticsCollectorFactory: AnalyticsCollector.Factory,
    private val looper: Looper

) : SDPlayer {


    private val mPlayerInfo = PlayerInfo()
    private var mIsLoading = false
    private var current: Track? = null
    private var transformerDisposable: Disposable? = null
    private val trackListeners = CopyOnWriteArraySet<TrackListener>()

    private val internalPlayer: SimpleExoPlayer =
        ExoPlayerFactory.newSimpleInstance(
            context,
            renderersFactory,
            trackSelector,
            loadControl,
            drmSessionManager,
            bandwidthMeter,
            analyticsCollectorFactory,
            looper
        )

    init {
        internalPlayer.addListener(object : Player.EventListener {

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (current == null) {
                    return
                }
                val playing = current!!

                when (playbackState) {
                    Player.STATE_IDLE -> {
                    }
                    Player.STATE_BUFFERING -> for (listener in trackListeners) {
                        mIsLoading = true
                        try {
                            listener.onTrackBuffering(playing)
                        } catch (e: Exception) {
                            Logger.onError(e)
                        }

                    }
                    Player.STATE_READY -> for (listener in trackListeners) {
                        mIsLoading = false
                        try {
                            listener.onTrackReady(playing)
                        } catch (e: Exception) {
                            Logger.onError(e)
                        }

                    }
                    Player.STATE_ENDED ->
                        if (playWhenReady) {
                            for (listener in trackListeners) {
                                try {
                                    listener.onTrackFinish(playing)
                                } catch (e: Exception) {
                                    Logger.onError(e)
                                }

                            }
                        }
                }
            }
        })
    }


    override fun play(track: Track, positionInMillis: Long, playWhenReady: Boolean) {
        stop()
        cancelTrackPreparing()
        mIsLoading = true
        for (listener in trackListeners) {
            try {
                listener.onTrackPreparing(track)
            } catch (e: Exception) {
                Logger.onError(e)
            }
        }

        current = track
        transformerDisposable = Single.fromCallable {
            try {
                Pair(track.transform(context), null)
            } catch (e: Exception) {
                Pair(track.transform(context), e)
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val mediaSource = it.first
                if (mediaSource == null) {
                    Logger.onError(it.second!!)
                    return@subscribe
                }
                internalPlayer.prepare(mediaSource)
                if (0 < positionInMillis) {
                    internalPlayer.seekTo(positionInMillis)
                }
                internalPlayer.playWhenReady = playWhenReady

            }, {
                Logger.onError(it)
            })

    }

    override fun pause() {
        internalPlayer.playWhenReady = false
    }

    override fun resume() {
        internalPlayer.playWhenReady = true
    }

    override fun stop() {
        internalPlayer.stop()
    }

    override fun release() {
        cancelTrackPreparing()
        internalPlayer.release()
        trackListeners.clear()
    }

    override fun getPlayerInfo(): PlayerInfo {
        setUpPlayerInfo()
        return mPlayerInfo
    }

    private fun setUpPlayerInfo() {
        mPlayerInfo.apply {
            currentTrack = current
            isPlaying = internalPlayer.playWhenReady
            isLoading = mIsLoading
        }
    }

    override fun addTrackListener(trackListener: TrackListener) {
        trackListeners.add(trackListener)
    }

    override fun removeTrackListener(trackListener: TrackListener) {
        trackListeners.remove(trackListener)
    }

    private fun cancelTrackPreparing() {
        transformerDisposable?.dispose()
    }

    public interface TrackListener {
        fun onTrackPreparing(track: Track)
        fun onTrackReady(track: Track)
        fun onTrackBuffering(track: Track)
        fun onTrackFinish(track: Track)
    }

    abstract class SimpleTrackListener : TrackListener {
        override fun onTrackBuffering(track: Track) {
        }

        override fun onTrackFinish(track: Track) {
        }

        override fun onTrackPreparing(track: Track) {
        }

        override fun onTrackReady(track: Track) {
        }
    }
}