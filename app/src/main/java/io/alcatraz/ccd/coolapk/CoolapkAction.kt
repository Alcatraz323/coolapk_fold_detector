package io.alcatraz.ccd.coolapk

import com.zhy.http.okhttp.request.RequestCall
import io.alcatraz.ccd.coolapk.network.RequestFactory

object CoolapkAction {
    enum class User(private val apiAddress: String, private val hasParams: Boolean) {
        FanList("fansList?uid=%s&page=%s", true),
        Space("space?uid=%s", true);

        fun createRequest(vararg params: String?): RequestCall {
            var url = Config.apiEntrance + actionEntrance
            var combineAction = ""
            if (hasParams) {
                val raw = apiAddress.split("%s")
                for ((index, i) in params.withIndex()) {
                    combineAction += raw[index]
                    combineAction += i
                }
            }
            url += combineAction
            val builder = RequestFactory.createApiGetRequest()
            builder.url(url)

            return builder.build()
        }

        companion object {
            const val actionEntrance = "user/"
        }
    }

    enum class Feed(private val apiAddress: String, private val hasParams: Boolean) {
        Detail("detail?id=%s", true);

        fun createRequest(vararg params: String?): RequestCall {
            var url = Config.apiEntrance + actionEntrance
            var combineAction = ""
            if (hasParams) {
                val raw = apiAddress.split("%s")
                for ((index, i) in params.withIndex()) {
                    combineAction += raw[index]
                    combineAction += i
                }
            }
            url += combineAction
            val builder = RequestFactory.createApiGetRequest()
            builder.url(url)

            return builder.build()
        }

        companion object {
            const val actionEntrance = "feed/"
        }
    }

    enum class Apk(private val apiAddress: String, private val hasParams: Boolean) {
        Detail("detail?id=%s", true);

        fun createRequest(vararg params: String?): RequestCall {
            var url = Config.apiEntrance + actionEntrance
            var combineAction = ""
            if (hasParams) {
                val raw = apiAddress.split("%s")
                for ((index, i) in params.withIndex()) {
                    combineAction += raw[index]
                    combineAction += i
                }
            }
            url += combineAction
            val builder = RequestFactory.createApiGetRequest()
            builder.url(url)

            return builder.build()
        }

        companion object {
            const val actionEntrance = "apk/"
        }
    }
}
