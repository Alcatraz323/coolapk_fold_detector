package io.alcatraz.ccd.beans

import okhttp3.Headers

data class NetworkElement(
    var url: String,
    var time: Double,
    var timeStamp: String,
    var method: String,
    var body: String,
    var headers: Headers?,
    var isResponse: Boolean
)