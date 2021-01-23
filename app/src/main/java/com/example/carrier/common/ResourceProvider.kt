package com.example.carrier.common

import android.content.Context
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import java.io.InputStream
import javax.inject.Inject

class ResourceProvider @Inject constructor(
    private val context: Context
) {
    fun getRawResource(@RawRes resource: Int): InputStream {
        return context.resources.openRawResource(resource)
    }

    fun getStringRes(@StringRes resource: Int): String = context.getString(resource)
}