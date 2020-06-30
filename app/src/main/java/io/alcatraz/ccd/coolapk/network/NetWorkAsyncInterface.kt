package io.alcatraz.ccd.coolapk.network

interface NetWorkAsyncInterface<T> {
    fun onSuccessObj(hasObj: Boolean, obj: T?)
    fun onSuccessRaw(strRaw: String?)
    fun onFailure(strErr: String)
}