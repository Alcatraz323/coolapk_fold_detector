package io.alcatraz.ccd.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController

import io.alcatraz.ccd.LogBuff
import io.alcatraz.ccd.R
import io.alcatraz.ccd.adapters.NetlogExpandableAdapter
import io.alcatraz.ccd.adapters.SetupPagerAdapter
import io.alcatraz.ccd.beans.SetupPage
import io.alcatraz.ccd.databinding.ElementLogboxBinding
import io.alcatraz.ccd.databinding.ElementNetboxBinding
import io.alcatraz.ccd.extended.CompatWithPipeActivity
import kotlinx.android.synthetic.main.activity_log.*

class LogActivity : CompatWithPipeActivity() {
    lateinit var logBinding: ElementLogboxBinding
    lateinit var netboxBinding: ElementNetboxBinding
    lateinit var pagerAdapter: SetupPagerAdapter
    lateinit var netlogExpandableAdapter: NetlogExpandableAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)
        initViews()
        initData()
    }

    private fun initViews() {
        setSupportActionBar(log_toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        logBinding = ElementLogboxBinding.inflate(layoutInflater)
        netboxBinding = ElementNetboxBinding.inflate(layoutInflater)
        pagerAdapter = SetupPagerAdapter(
            listOf(
                SetupPage(getString(R.string.log_main), logBinding.root),
                SetupPage(getString(R.string.log_net), netboxBinding.root)
            ), this
        )
        log_view_pager.adapter = pagerAdapter
        netlogExpandableAdapter = NetlogExpandableAdapter(this, LogBuff.getNetlogList())
        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_fall_down)
        netboxBinding.netlogList.layoutAnimation = controller
        netboxBinding.netlogList.setAdapter(netlogExpandableAdapter)
        log_tab_layout.setupWithViewPager(log_view_pager)
    }

    private fun initData() {
        logBinding.logConsoleBox.text = LogBuff.finalLog
        netlogExpandableAdapter.setNewData(LogBuff.getNetlogList())
        netboxBinding.netlogList.scheduleLayoutAnimation()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = MenuInflater(this)
        menuInflater.inflate(R.menu.activity_log_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_log_refresh -> initData()
        }
        return super.onOptionsItemSelected(item)
    }
}
