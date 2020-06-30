package io.alcatraz.ccd.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import io.alcatraz.ccd.R
import io.alcatraz.ccd.coolapk.CoolapkApiAsync
import io.alcatraz.ccd.coolapk.databeans.feed.detail.Detail
import io.alcatraz.ccd.coolapk.network.NetWorkAsyncInterface
import io.alcatraz.ccd.extended.CompatWithPipeActivity
import kotlinx.android.synthetic.main.activity_feed_fold.*

class FoldFeedActivity : CompatWithPipeActivity() {
    lateinit var url: String
    lateinit var dataBean: Detail
    lateinit var feedId: String

    companion object {
        const val KEY_FEED_URL = "alc_coolapk_fold_feed_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_fold)
        setSupportActionBar(fold_feed_toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        initialize()
    }

    private fun initialize() {
        url = intent.getStringExtra(KEY_FEED_URL)!!
        processDetail()
        initData()
    }

    private fun processDetail() {
        val process1 = url.split("?")
        val process2 = process1[0].split("/")
        feedId = process2[process2.size - 1]
    }

    private fun updateViews() {
        fold_feed_content_title.append("(by:" + dataBean.data?.username + ")")
        fold_feed_content.text = dataBean.data?.message
        fold_feed_recommended.text = if (dataBean.data?.recommend == 1) {
            "true"
        } else {
            "false"
        }

        fold_feed_headline.text = if (dataBean.data?.is_headline == 1) {
            "true"
        } else {
            "false"
        }

        fold_feed_hidden.text = if (dataBean.data?.is_hidden == 1) {
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
        CoolapkApiAsync.Feed.requestFeedDetail(feedId, object : NetWorkAsyncInterface<Detail> {
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