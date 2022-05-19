package com.iuturakulov.hseapple.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.iuturakulov.hseapple.R


class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            if (isLoggedIn()) {
                val startupIntent = Intent(this, AvailableCoursesActivity::class.java)
                startupIntent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(startupIntent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            } else {
                val startupIntent = Intent(this, LoginAuthActivity::class.java)
                startActivity(startupIntent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        }, 1000)
    }

    private fun isLoggedIn(): Boolean {
        // Check SharedPreferences or wherever you store login information
        return getSharedPreferences(
            "my_app_preferences",
            Context.MODE_PRIVATE
        ).getBoolean("loggedIn", false)
    }
}
