package me.androidbox.qrcraft.core.di

import me.androidbox.qrcraft.SaveQRCraftImp
import me.androidbox.qrcraft.core.data.db.DatabaseFactory
import me.androidbox.qrcraft.features.scan_result.data.SaveQRCraft
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {

        single { DatabaseFactory(androidApplication()) }

        factory {
            SaveQRCraftImp(androidContext())
        }.bind(SaveQRCraft::class)
    }