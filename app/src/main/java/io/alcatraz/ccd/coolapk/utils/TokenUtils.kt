package io.alcatraz.ccd.coolapk.utils

import android.util.Base64
import java.lang.Long
import java.security.MessageDigest
import java.util.*

object TokenUtils {
    fun encrypt(dataStr: String): String? {
        try {
            val m: MessageDigest = MessageDigest.getInstance("MD5")
            m.update(dataStr.toByteArray(charset("UTF8")))
            val s: ByteArray = m.digest()
            var result = ""
            for (i in s.indices) {
                result += Integer.toHexString(0x000000FF and s[i].toInt() or -0x100)
                    .substring(6)
            }
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getAuthToken(): String {
        val token =
            "token://com.coolapk.market/c67ef5943784d09750dcfbb31020f0ab?%s$%s&com.coolapk.market"
        val currentTime = System.currentTimeMillis()/1000
        val timeStrMD5 = encrypt(currentTime.toString())
        val tempUUID = UUID.randomUUID().toString()
        val combinedToken = String.format(token, timeStrMD5, tempUUID)
        val combinedB64 = Base64.encodeToString(
            combinedToken.toByteArray(Charsets.UTF_8),
            Base64.DEFAULT
        )
        val secondMD5 = encrypt(combinedB64.replace("\n",""))
        return secondMD5 + tempUUID + "0x" + Long.toHexString(currentTime)
    }

    fun generateRandomInteger(length: Int): Int{
        return ((Math.random() * 9 + 1) * Math.pow(10.0, (length - 1).toDouble())).toInt()
    }
}