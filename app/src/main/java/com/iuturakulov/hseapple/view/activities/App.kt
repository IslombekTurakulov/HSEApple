package com.iuturakulov.hseapple.view.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.appcompat.app.AppCompatDelegate.setCompatVectorFromResourcesEnabled
import com.cometchat.pro.core.AppSettings
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.uikit.ui_settings.UIKitSettings
import com.hse.auth.di.AuthComponent
import com.hse.auth.di.AuthComponentProvider
import com.hse.core.BaseApplication
import com.iuturakulov.hseapple.BuildConfig
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.utils.API_COUNTRY
import com.iuturakulov.hseapple.utils.APP_ID
import com.iuturakulov.hseapple.utils.AUTH_KEY
import timber.log.Timber

class App : BaseApplication(), AuthComponentProvider {

    override fun onCreate() {
        super.onCreate()
        val appSettings =
            AppSettings.AppSettingsBuilder().subscribePresenceForAllUsers().setRegion(API_COUNTRY)
                .build()
        CometChat.init(this, APP_ID, appSettings, object : CometChat.CallbackListener<String>() {
            override fun onSuccess(successMessage: String) {
                UIKitSettings.setAppID(APP_ID)
                UIKitSettings.setAuthKey(AUTH_KEY)
                CometChat.setSource("ui-kit", "android", "kotlin")
                Timber.d("Initialization completed successfully")
            }

            override fun onError(e: CometChatException) {
                Timber.d("Initialization failed with exception: %s", e.message)
            }
        })
        createNotificationChannel()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        (appComponent as AppComponent).inject(this)
        setCompatVectorFromResourcesEnabled(true)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun provideAuthComponent(): AuthComponent {
        return (appComponent as AppComponent).loginComponent().create()
    }

    private fun createNotificationChannel() {
        val name: CharSequence = getString(R.string.app_name)
        val description = "channel_description"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("2", name, importance)
        channel.description = description

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        private const val TAG = "App"
    }
}