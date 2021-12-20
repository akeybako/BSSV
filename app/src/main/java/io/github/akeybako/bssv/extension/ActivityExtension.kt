package io.github.akeybako.bssv.extension

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import io.github.akeybako.bssv.R

fun Activity.showCustomTab(url: String) {

    val customTabsIntent = CustomTabsIntent.Builder()
        .setShowTitle(true)
        .setUrlBarHidingEnabled(true)
        .build()

    customTabsIntent.intent.setPackage("com.android.chrome")
    customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

    try {
        customTabsIntent.launchUrl(this, Uri.parse(url))
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, R.string.error_on_open_browser, Toast.LENGTH_SHORT).show()
    }
}