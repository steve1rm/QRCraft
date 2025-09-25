package me.androidbox.qrcraft

import android.app.Application
import android.content.Context
import me.androidbox.qrcraft.core.di.initKoin
import org.koin.android.ext.koin.androidContext

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        init(this)

        initKoin {
            androidContext(this@Application)
        }

    }

    companion object {
        lateinit var applicationContext: Context
            private set

        fun init(context: Context) {
            applicationContext = context.applicationContext
        }
    }
}