package com.sdpodcast.lib_player.core

import android.annotation.SuppressLint
import android.net.Uri
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.util.Util


class DefaultMediaSourceCreator {
    companion object {
        @SuppressLint("SwitchIntDef")
        fun createMediaSourceFrom(@NonNull dataSourceFactory: DataSource.Factory, @NonNull uri: Uri, @Nullable overrideExtension: String? = null): MediaSource {
            @C.ContentType val contentType = Util.inferContentType(uri, overrideExtension)
            when (contentType) {
                C.TYPE_OTHER -> return ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(uri)
                else -> {
                    throw IllegalStateException("Unsupported type: $contentType")
                }
            }
        }
    }
}