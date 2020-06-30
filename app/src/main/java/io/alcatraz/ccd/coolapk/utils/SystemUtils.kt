package io.alcatraz.ccd.coolapk.utils

import android.content.Context
import android.provider.Settings.Secure
import android.telephony.TelephonyManager
import java.net.NetworkInterface
import java.util.*


object SystemUtils {
    fun getAndroidId(context: Context): String? {
        return try {
            Secure.getString(context.getContentResolver(), "android_id")
        } catch (e: Exception) {
            e.toString()
            null
        }
    }

    fun getImeiOrMeid(context: Context): String? {
        return try {
            (context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).deviceId
        } catch (e: SecurityException) {
            e.toString()
            null
        }
    }

    fun getImsi(context: Context): String? {
        return try {
            (context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).subscriberId
        } catch (e: SecurityException) {
            e.toString()
            null
        }
    }

    fun getMacAddress(context: Context): String? {
        return try {
            val networkInterfaces = NetworkInterface.getNetworkInterfaces ()

            var networkInterface:NetworkInterface
            do {
                if (!networkInterfaces.hasMoreElements()) {
                    return null
                }

                networkInterface = networkInterfaces.nextElement ()
            } while (!"wlan0".equals(networkInterface.getName()) && !"eth0".equals(networkInterface.getName()))

            var hardwareAddress = networkInterface.hardwareAddress
            if (hardwareAddress != null && hardwareAddress.isNotEmpty()) {
                val stringBuilder = StringBuilder()
                for ((index, i) in hardwareAddress.withIndex()) {
                    stringBuilder.append(String.format("%02X:", hardwareAddress[index]))
                }

                if (stringBuilder.isNotEmpty()) {
                    stringBuilder.deleteCharAt(stringBuilder.length - 1)
                }

                stringBuilder.toString().toLowerCase(Locale.getDefault())
            } else {
                null
            }
        } catch (e: java.lang.Exception) {
            "02:00:00:00:00:00"
        }
    }


}