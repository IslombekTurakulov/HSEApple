package com.iuturakulov.hseapple.ui.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.iuturakulov.hseapple.R
import kotlinx.android.synthetic.main.activity_about_app.*
import kotlinx.android.synthetic.main.toolbar_about_app.*
import timber.log.Timber


class AboutAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_app)
        back_arrow_about.setOnClickListener {
            onBackPressed()
        }
        privacyPolicyBtn.setOnClickListener {
            openButtonUrl(url = "https://www.hse.ru/data_protection_regulation")
        }
        openSourceCode.setOnClickListener {
            openButtonUrl(url = "https://github.com/IslombekTurakulov/HSEApple")
        }
    }

    private fun openButtonUrl(url: String) {
        var parsedUrl: Uri? = null
        try {
            parsedUrl = Uri.parse(url)
        } catch (ex: Exception) {
            Timber.d("Failed to parse input to a URL. Input: $url")
            Toast.makeText(this, "Failed to parse URL", Toast.LENGTH_LONG).show()
        }

        if (parsedUrl != null) {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = parsedUrl
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(
                    this,
                    "Can't open URL",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}