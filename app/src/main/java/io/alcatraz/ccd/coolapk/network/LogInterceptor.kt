package io.alcatraz.ccd.coolapk.network

import io.alcatraz.ccd.LogBuff
import io.alcatraz.ccd.LogBuff.getTime
import io.alcatraz.ccd.beans.NetworkElement
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class LogInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val t1 = System.nanoTime()
        var requestBody = ""
        if (request.body() != null) {
            requestBody = request.body().toString()
        }
        val requestLog = NetworkElement(
            request.url().toString(),
            0.0,
            getTime(),
            request.method(),
            requestBody,
            request.headers(),
            false
        )
        LogBuff.addNetLog(requestLog)

        val response = chain.proceed(request)

        val t2 = System.nanoTime()

        val responseBody = response.peekBody(1024 * 1024.toLong())
        val responseLog = NetworkElement(
            response.request().url().toString(),
            (t2 - t1) / 1e6,
            getTime(),
            "Response",
            responseBody.string(),
            response.headers(),
            true
        )
        LogBuff.addNetLog(responseLog)
        return response
    }
}