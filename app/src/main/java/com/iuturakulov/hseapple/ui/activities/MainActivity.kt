package com.iuturakulov.hseapple.ui.activities

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.cometchat.pro.uikit.ui_resources.constants.UIKitConstants
import com.cometchat.pro.uikit.ui_resources.utils.Utils
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.utils.APP_ACTIVITY
import com.iuturakulov.hseapple.utils.preferences
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.main_fragment)
        APP_ACTIVITY = this
        preferences = getSharedPreferences(
            "${packageName}_preferences", Context.MODE_PRIVATE
        )
        setupSmoothBottomMenu()
        if (!Utils.hasPermissions(
                this, Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                UIKitConstants.RequestCode.RECORD
            )
        }
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
