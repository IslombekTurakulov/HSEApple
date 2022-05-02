package com.iuturakulov.hseapple.view.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate.setCompatVectorFromResourcesEnabled
import com.cometchat.pro.core.AppSettings
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.uikit.ui_components.calls.call_manager.listener.CometChatCallListener
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
        val appSettings = AppSettings.AppSettingsBuilder().subscribePresenceForAllUsers().setRegion(
            API_COUNTRY).build()
        CometChat.init(this, APP_ID, appSettings, object : CometChat.CallbackListener<String>() {
            override fun onSuccess(s: String) {
                UIKitSettings.setAppID(APP_ID)
                UIKitSettings.setAuthKey(AUTH_KEY)
                CometChat.setSource("ui-kit", "android", "kotlin")
                Log.d(TAG, "onSuccess: $s")
            }

            override fun onError(e: CometChatException) {
            }
        })
        val uiKitSettings = UIKitSettings(this)
        uiKitSettings.addConnectionListener(TAG)
        CometChatCallListener.addCallListener(TAG, this)
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
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name: CharSequence = getString(R.string.app_name)
        val description = "Description"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("2", name, importance)
        channel.description = description
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    override fun onTerminate() {
        super.onTerminate()
        CometChatCallListener.removeCallListener(TAG)
        CometChat.removeConnectionListener(TAG)
    }
    companion object {
        private const val TAG = "App"
    }
}