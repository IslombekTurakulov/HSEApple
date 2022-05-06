package com.iuturakulov.hseapple.utils

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.view.activities.MainActivity
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
