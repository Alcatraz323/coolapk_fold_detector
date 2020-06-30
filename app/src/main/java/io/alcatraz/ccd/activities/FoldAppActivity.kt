package io.alcatraz.ccd.activities

import android.os.Bundle
import android.view.MenuItem
import io.alcatraz.ccd.R
import io.alcatraz.ccd.coolapk.CoolapkApiAsync
import io.alcatraz.ccd.coolapk.databeans.apk.detail.Detail
import io.alcatraz.ccd.coolapk.network.NetWorkAsyncInterface
import io.alcatraz.ccd.extended.CompatWithPipeActivity
import io.alcatraz.ccd.utils.Utils
import kotlinx.android.synthetic.main.activity_app_fold.*

class FoldAppActivity : CompatWithPipeActivity() {
    lateinit var id: String
    lateinit var dataBean: Detail

    companion object {
        const val KEY_APK_NAME = "alc_coolapk_fold_apk_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_fold)
        setSupportActionBar(fold_app_toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        initialize()
    }

    private fun initialize() {
        id = intent.getStringExtra(KEY_APK_NAME)!!
        initData()
    }

    private fun updateViews() {
        fold_app_name.append(dataBean.data?.title)
        fold_app_package.text = dataBean.data?.apkname
        fold_app_publish_state.text = dataBean.data?.pubStatusText
        var tags = "None"
        if (dataBean.data?.tagList?.size?:0 > 0) {
            tags = Utils.extractStringArr(dataBean.data?.tagList?.toTypedArray()!!)
        }
        fold_app_tags.text = tags
        fold_app_keywords.text = dataBean.data?.apkKeywords
        fold_app_recommend.text = if (dataBean.data?.recommend == 1) {
            "true"
        } else {
            "false"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initData() {
        CoolapkApiAsync.Apk.requestApkDetail(id, object : NetWorkAsyncInterface<Detail> {
            override fun onSuccessObj(hasObj: Boolean, obj: Detail?) {
                if (hasObj) {
                    dataBean = obj!!
                    runOnUiThread {
                        updateViews()
                    }
                }
            }

            override fun onSuccessRaw(strRaw: String?) {

            }

            override fun onFailure(strErr: String) {

            }
        })
    }
}