package io.alcatraz.ccd

import android.annotation.SuppressLint
import android.text.Html
import io.alcatraz.ccd.beans.NetworkElement
import java.text.SimpleDateFormat
import java.util.*


object LogBuff {
    var MESSAGE_DEFAULT_MAX_AMOUNT = 256

    var COLOR_LEVEL_INFO = "#4caf50"
    var COLOR_LEVEL_WARN = "#ff9800"
    var COLOR_LEVEL_ERROR = "#f44336"
    var COLOR_LEVEL_DEBUG = "#1565C0"
    var HTML_BRLINE = "<br/>"

    private var whole_message = ""
    private var num = 0

    private val netlog: LinkedList<NetworkElement> = LinkedList<NetworkElement>()
    private var netlogMax = 128

    val finalLog: CharSequence
        get() = Html.fromHtml(whole_message)

    @JvmStatic
    fun getTime(): String {
        val currentTime = System.currentTimeMillis()
        @SuppressLint("SimpleDateFormat") val formatter = SimpleDateFormat("MM-dd HH:mm:ss")
        val date = Date(currentTime)
        return formatter.format(date)
    }

    fun I(infoMsg: String) {
        val className = Exception().stackTrace[1].className.replace(BuildConfig.APPLICATION_ID, "")
        val methodName = Exception().stackTrace[1].methodName
        commitMessageChange(
            wrapFontString(
                getTime() + " [INFO][" + className + "::" + methodName + "]" +
                        infoMsg, COLOR_LEVEL_INFO
            )
        )
    }

    fun W(warnMsg: String) {
        val className = Exception().stackTrace[1].className.replace(BuildConfig.APPLICATION_ID, "")
        val methodName = Exception().stackTrace[1].methodName
        commitMessageChange(
            wrapFontString(
                getTime() + " [WARNING][" + className + "::" + methodName + "]" +
                        warnMsg, COLOR_LEVEL_WARN
            )
        )
    }

    fun E(errMsg: String) {
        val className = Exception().stackTrace[1].className.replace(BuildConfig.APPLICATION_ID, "")
        val methodName = Exception().stackTrace[1].methodName
        commitMessageChange(
            wrapFontString(
                getTime() + " [ERROR][" + className + "::" + methodName + "]" +
                        errMsg, COLOR_LEVEL_ERROR
            )
        )
    }

    fun D(dbgMsg: String) {
        val className = Exception().stackTrace[1].className.replace(BuildConfig.APPLICATION_ID, "")
        val methodName = Exception().stackTrace[1].methodName
        commitMessageChange(
            wrapFontString(
                getTime() + " [DEBUG][" + className + "::" + methodName + "]" +
                        dbgMsg, COLOR_LEVEL_DEBUG
            )
        )
    }

    @JvmOverloads
    fun wrapFontString(
        raw: String,
        rgb_color: String,
        isBold: Boolean = false,
        isItalic: Boolean = false
    ): String {
        var rawVar = raw
        if (isBold) {
            rawVar = "<b>$rawVar</b>"
        }
        if (isItalic) {
            rawVar = "<i>$rawVar</i>"
        }

        return "<font color=\"$rgb_color\">$rawVar</font>"
    }

    fun log(content: String) {
        commitMessageChange(content)
    }

    fun addDivider() {
        whole_message += "<br/>============================"
    }

    fun clearLog() {
        whole_message = ""
        num = 0
        W("Cleared operation log")
    }

    fun getNetlogList(): List<NetworkElement> {
        val current: MutableList<NetworkElement> = LinkedList()
        current.addAll(netlog)
        return Collections.unmodifiableList(current)
    }

    fun addNetLog(element: NetworkElement?) {
        if (netlog.size >= netlogMax) {
            netlog.clear()
            W("Automatically cleared netlog ( reaching max :$netlogMax)")
        }
        netlog.add(element ?: return)
    }

    fun clearNetlog() {
        netlog.clear()
        W("Cleared Netlog")
    }

    private fun checkAndClearup() {
        if (num >= MESSAGE_DEFAULT_MAX_AMOUNT) {
            whole_message = ""
            num = 0
            W("Automatically cleared log ( reaching max :$MESSAGE_DEFAULT_MAX_AMOUNT)")
        }
    }

    private fun commitMessageChange(content: String) {
        checkAndClearup()
        whole_message += HTML_BRLINE + content
        num++
    }
}
