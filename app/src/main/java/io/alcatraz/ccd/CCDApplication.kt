package io.alcatraz.ccd

import android.app.Application
import android.content.Context
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.cookie.CookieJarImpl
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore
import io.alcatraz.ccd.coolapk.network.LogInterceptor

import okhttp3.OkHttpClient




class CCDApplication : Application() {
    var overallContext: Context? = null
        private set

    //TODO : Check string.xml/Setup versionCode/build.gradle when release update
    //TODO : Set Empty View for all adapter views
    override fun onCreate() {
        overallContext = applicationContext
        super.onCreate()
        val cookieStore = PersistentCookieStore(overallContext)
        val cookieJar = CookieJarImpl(cookieStore)
        val okHttpClient = OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .addInterceptor(LogInterceptor())
            .build()
        OkHttpUtils.initClient(okHttpClient)
    }

}
