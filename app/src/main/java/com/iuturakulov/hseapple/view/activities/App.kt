package com.iuturakulov.hseapple.view.activities

import androidx.appcompat.app.AppCompatDelegate.setCompatVectorFromResourcesEnabled
import com.hse.auth.di.AuthComponent
import com.hse.auth.di.AuthComponentProvider
import com.hse.core.BaseApplication
import com.iuturakulov.hseapple.BuildConfig
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
}