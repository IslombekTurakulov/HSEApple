package com.iuturakulov.hseapple.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64.encodeToString
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.view.activities.MainActivity
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*



lateinit var APP_ACTIVITY: MainActivity

private var toast: Toast? = null

internal fun Activity.toast(message: CharSequence) {
    toast?.cancel()
    toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        .apply { show() }
}


fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}

fun restartActivity() {
    val intent = Intent(APP_ACTIVITY, MainActivity::class.java)
    APP_ACTIVITY.startActivity(intent)
    APP_ACTIVITY.finish()
}

fun replaceFragment(fragment: Fragment, addStack: Boolean = true) {
    if (addStack) {
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.main_container,
                fragment
            ).commitAllowingStateLoss()
    } else {
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_container,
                fragment
            ).commitAllowingStateLoss()
    }
}

fun replaceFragment(fragment: Fragment, id: Int, addStack: Boolean = true) {
    if (addStack) {
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(
                id,
                fragment
            ).commitAllowingStateLoss()
    } else {
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .replace(
                id,
                fragment
            ).commitAllowingStateLoss()
    }
}

fun String.asDateTime(): String {
    val timeFormat = SimpleDateFormat("d MMMM HH:mm", Locale.getDefault())
    return timeFormat.format(Date(this.toLong()))
}

fun TextInputEditText.validateTitle(): Boolean {
    if (!this.text.isNullOrEmpty()) {
        this.setText(this.text?.trim())
        return this.text!!.length in 4..50
    }
    return false
}

fun TextInputEditText.validateDescription(): Boolean {
    if (!this.text.isNullOrEmpty()) {
        this.setText(this.text?.trim())
        return this.text!!.length in 0..256
    }
    return false
}

fun TextInputEditText.validateNews(): Boolean {
    if (!this.text.isNullOrEmpty()) {
        this.setText(this.text?.trim())
        return this.text!!.length in 0..1024
    }
    return false
}

internal fun Calendar.formatDateTime(): String {
    return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(this.time)
}

fun ImageView.setImage(url: String, default: Int) {
    Picasso.get()
        .load(url)
        .fit()
        .placeholder(default)
        .into(this)
}
