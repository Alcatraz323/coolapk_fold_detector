package io.alcatraz.ccd.coolapk.network

import android.util.Log
import io.alcatraz.ccd.LogBuff
import okhttp3.Request

object ErrUtils {
    fun packupErrBean(request: Request, e: Exception, id: Int): String {
        return "Exception(Message=" + e.message + ") occurred when request id=" + id + " requesting url:" + request.url()
    }

    fun commitRequestException(
        request: Request,
        e: Exception,
        id: Int
    ) {
        Log.e(NetWorkConstants.SDK_NAME, packupErrBean(request, e, id))
        LogBuff.E(packupErrBean(request, e, id))
        e.printStackTrace()
    }

}