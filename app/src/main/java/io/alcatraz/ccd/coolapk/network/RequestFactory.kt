package io.alcatraz.ccd.coolapk.network

import android.os.Build
import android.util.Base64
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.builder.GetBuilder
import com.zhy.http.okhttp.cookie.CookieJarImpl
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore
import io.alcatraz.ccd.LogBuff
import io.alcatraz.ccd.coolapk.utils.TokenUtils


@Suppress("MemberVisibilityCanBePrivate")
object RequestFactory {
    fun combineDeviceCode(): String {
        var deviceRaw: String = TokenUtils.generateRandomInteger(16).toString()
        deviceRaw += "; "
        deviceRaw += TokenUtils.generateRandomInteger(15).toString()
        deviceRaw += "; "
        deviceRaw += TokenUtils.generateRandomInteger(15).toString()
        deviceRaw += "; "
        deviceRaw += "02:00:00:00:00:00"
        deviceRaw += "; "
        deviceRaw += Build.MANUFACTURER
        deviceRaw += "; "
        deviceRaw += Build.BRAND
        deviceRaw += "; "
        deviceRaw += Build.MODEL
        LogBuff.I(deviceRaw)
        var deviceB64 = Base64.encodeToString(deviceRaw.toByteArray(), Base64.DEFAULT)
        deviceB64 = StringBuilder(deviceB64).reverse().toString()
        deviceB64 = Regex("\r\n|\r|\n|=").replace(deviceB64, "")
        return deviceB64
    }

    @Synchronized
    fun createApiGetRequest(): GetBuilder {
        val builder = OkHttpUtils.get()
        builder.addHeader(
            "User-Agent",
            " +CoolMarket/10.3.1-2006162"
        )
        builder.addHeader("X-Requested-With", "XMLHttpRequest")
        builder.addHeader("X-Sdk-Int", Build.VERSION.SDK_INT.toString())
        builder.addHeader("X-Sdk-Locale", "zh-CN")
        builder.addHeader("X-App-Id", "com.coolapk.market")
        builder.addHeader("X-App-Token", TokenUtils.getAuthToken())
        builder.addHeader("X-App-Version", "10.3.1")
        builder.addHeader("X-App-Code", "2006162")
        builder.addHeader("X-Api-Version", "10")
        builder.addHeader(
            "X-App-Device",
            combineDeviceCode()
        )
        builder.addHeader("X-Dark-Mode", "0")
        return builder
    }
}