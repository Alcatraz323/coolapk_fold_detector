package io.alcatraz.ccd.coolapk

import com.zhy.http.okhttp.callback.StringCallback
import io.alcatraz.ccd.coolapk.databeans.feed.detail.Detail
import io.alcatraz.ccd.coolapk.network.ErrUtils
import io.alcatraz.ccd.coolapk.network.NetWorkAsyncInterface
import io.alcatraz.ccd.coolapk.databeans.user.fanlist.FanList
import io.alcatraz.ccd.coolapk.databeans.user.space.Space
import io.alcatraz.ccd.utils.Utils
import okhttp3.Call

object CoolapkApiAsync {
    object User {
        fun requestFanList(
            uid: String,
            page: Int,
            networkAsyncInterface: NetWorkAsyncInterface<FanList>
        ) {
            val request = CoolapkAction.User.FanList.createRequest(uid, page.toString())
            request.execute(object : StringCallback() {
                override fun onResponse(p0: String?, p1: Int) {
                    networkAsyncInterface.onSuccessRaw(p0)
                    try {
                        networkAsyncInterface.onSuccessObj(
                            true,
                            Utils.json2Object(p0 ?: "", FanList::class.java)
                        )
                    } catch (e: Exception) {
                        networkAsyncInterface.onSuccessObj(false, null)
                    }
                }

                override fun onError(p0: Call?, p1: Exception?, p2: Int) {
                    doOkHttpException(networkAsyncInterface, p0!!, p1, p2)
                }
            })
        }

        fun requestAccountSpace(uid: String, networkAsyncInterface: NetWorkAsyncInterface<Space>) {
            val request = CoolapkAction.User.Space.createRequest(uid)
            request.execute(object : StringCallback() {
                override fun onResponse(p0: String?, p1: Int) {
                    networkAsyncInterface.onSuccessRaw(p0)
                    try {
                        networkAsyncInterface.onSuccessObj(
                            true,
                            Utils.json2Object(p0 ?: "", Space::class.java)
                        )
                    } catch (e: Exception) {
                        networkAsyncInterface.onSuccessObj(false, null)
                    }
                }

                override fun onError(p0: Call?, p1: Exception?, p2: Int) {
                    doOkHttpException(networkAsyncInterface, p0!!, p1, p2)
                }
            })
        }
    }

    object Feed{
        fun requestFeedDetail(id: String, networkAsyncInterface: NetWorkAsyncInterface<Detail>) {
            val request = CoolapkAction.Feed.Detail.createRequest(id)
            request.execute(object : StringCallback() {
                override fun onResponse(p0: String?, p1: Int) {
                    networkAsyncInterface.onSuccessRaw(p0)
                    try {
                        networkAsyncInterface.onSuccessObj(
                            true,
                            Utils.json2Object(p0 ?: "", Detail::class.java)
                        )
                    } catch (e: Exception) {
                        networkAsyncInterface.onSuccessObj(false, null)
                    }
                }

                override fun onError(p0: Call?, p1: Exception?, p2: Int) {
                    doOkHttpException(networkAsyncInterface, p0!!, p1, p2)
                }
            })
        }
    }

    object Apk{
        fun requestApkDetail(id: String, networkAsyncInterface: NetWorkAsyncInterface<io.alcatraz.ccd.coolapk.databeans.apk.detail.Detail>) {
            val request = CoolapkAction.Apk.Detail.createRequest(id)
            request.execute(object : StringCallback() {
                override fun onResponse(p0: String?, p1: Int) {
                    networkAsyncInterface.onSuccessRaw(p0)
                    try {
                        networkAsyncInterface.onSuccessObj(
                            true,
                            Utils.json2Object(p0 ?: "", io.alcatraz.ccd.coolapk.databeans.apk.detail.Detail::class.java)
                        )
                    } catch (e: Exception) {
                        networkAsyncInterface.onSuccessObj(false, null)
                    }
                }

                override fun onError(p0: Call?, p1: Exception?, p2: Int) {
                    doOkHttpException(networkAsyncInterface, p0!!, p1, p2)
                }
            })
        }
    }

    fun <T> doOkHttpException(
        callback: NetWorkAsyncInterface<T>,
        call: Call,
        e: Exception?,
        i: Int
    ) {
        callback.onFailure(ErrUtils.packupErrBean(call.request(), e ?: Exception(), i))
        ErrUtils.commitRequestException(call.request(), e ?: Exception(), i)
    }
}

