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

    companion object {
        private const val TAG = "App"
    }
}