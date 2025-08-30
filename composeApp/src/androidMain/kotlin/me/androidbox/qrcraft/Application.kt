package me.androidbox.qrcraft

import android.app.Application
import me.androidbox.qrcraft.core.di.initKoin
import me.androidbox.qrcraft.core.di.qrModule
import org.koin.android.ext.koin.androidContext

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@Application)
            modules(qrModule)
        }

    }
}