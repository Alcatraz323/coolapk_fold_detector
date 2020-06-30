package io.alcatraz.ccd.activities

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import io.alcatraz.ccd.Constants
import io.alcatraz.ccd.R
import io.alcatraz.ccd.coolapk.CoolapkApiAsync
import io.alcatraz.ccd.coolapk.databeans.feed.detail.Detail
import io.alcatraz.ccd.coolapk.network.NetWorkAsyncInterface
import io.alcatraz.ccd.coolapk.databeans.user.space.Space
import io.alcatraz.ccd.databinding.DialogEdittextBinding
import io.alcatraz.ccd.extended.CompatWithPipeActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : CompatWithPipeActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = MenuInflater(this)
        menuInflater.inflate(R.menu.activity_main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_main_about -> startActivity(
                Intent(
                    this@MainActivity,
                    AboutActivity::class.java
                )
            )
            R.id.menu_log_refresh -> initData()
            R.id.menu_main_log -> startActivity(Intent(this@MainActivity, LogActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.main_card_help -> startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(Constants.SUPPORT_URL)
                )
            )
            R.id.main_card_status -> {
                val dialogBinding = DialogEdittextBinding.inflate(layoutInflater)
                dialogBinding.dialogEdittext.hint = getString(R.string.feed_id)
                AlertDialog.Builder(this)
                    .setTitle(R.string.main_card_statistic_title)
                    .setView(dialogBinding.root)
                    .setNegativeButton(R.string.ad_nb, null)
                    .setPositiveButton(
                        R.string.ad_pb
                    ) { dialog, _ ->
                        val intent = Intent(this@MainActivity, FoldFeedActivity::class.java)
                        intent.putExtra(
                            FoldFeedActivity.KEY_FEED_URL,
                            dialogBinding.dialogEdittext.text.toString()
                        )
                        startActivity(intent)
                        dialog.dismiss()
                    }.show()
            }
            R.id.main_card_history -> {
                val dialogBinding = DialogEdittextBinding.inflate(layoutInflater)
                dialogBinding.dialogEdittext.hint = getString(R.string.apk_pkg_name)
                AlertDialog.Builder(this)
                    .setTitle(R.string.main_card_history_title)
                    .setView(dialogBinding.root)
                    .setNegativeButton(R.string.ad_nb, null)
                    .setPositiveButton(
                        R.string.ad_pb
                    ) { dialog, _ ->
                        val intent = Intent(this@MainActivity, FoldAppActivity::class.java)
                        intent.putExtra(
                            FoldAppActivity.KEY_APK_NAME,
                            dialogBinding.dialogEdittext.text.toString()
                        )
                        startActivity(intent)
                        dialog.dismiss()
                    }.show()
            }
            R.id.main_card_setting -> toast(R.string.no_settings)
        }
    }

    private fun initialize() {
        initData()
        initViews()
    }

    private fun initViews() {
        setSupportActionBar(main_toolbar)
        main_card_history.setOnClickListener(this)
        main_card_setting.setOnClickListener(this)
        main_card_help.setOnClickListener(this)
        main_card_status.setOnClickListener(this)
    }

    private fun initData() {
    }
}