package com.iuturakulov.hseapple.view.activities

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.cometchat.pro.core.AppSettings
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.utils.APP_ACTIVITY
import com.iuturakulov.hseapple.utils.APP_ID
import com.iuturakulov.hseapple.utils.preferences
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.main_fragment)
        APP_ACTIVITY = this
        preferences = getSharedPreferences(
            "${packageName}_preferences", Context.MODE_PRIVATE
        )
        val appSettings =
            AppSettings.AppSettingsBuilder().subscribePresenceForAllUsers().setRegion("EUROPE")
                .build()
        CometChat.init(this, APP_ID, appSettings, object : CometChat.CallbackListener<String>() {
            override fun onSuccess(successMessage: String) {
                Timber.d("Initialization completed successfully")
            }

            override fun onError(e: CometChatException) {
                Timber.d("Initialization failed with exception: %s", e.message)
            }
        })
        setupSmoothBottomMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_chat -> {
                showToast("Another Menu Item 1 Selected")
            }
            R.id.tests_chat -> {
                showToast("Another Menu Item 2 Selected")
            }
            R.id.news_chat -> {
                showToast("Another Menu Item 3 Selected")
            }
            R.id.profile_person -> {
                showToast("Another Menu Item 4 Selected")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.menu)
        val menu = popupMenu.menu
        bottomBar.setupWithNavController(menu, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
