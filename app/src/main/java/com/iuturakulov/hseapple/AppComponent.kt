package com.iuturakulov.hseapple

import android.app.Application
import com.hse.auth.di.AuthComponent
import com.hse.auth.di.AuthModule
import com.hse.core.di.AppModule
import com.hse.core.di.BaseAppComponent
import com.hse.core.di.CoreModule
import com.iuturakulov.hseapple.ui.activities.LoginAuthActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AuthModule::class, CoreModule::class])
interface AppComponent : BaseAppComponent {

    fun inject(app: App)
    fun inject(activity: LoginAuthActivity)

    fun loginComponent(): AuthComponent.Factory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}