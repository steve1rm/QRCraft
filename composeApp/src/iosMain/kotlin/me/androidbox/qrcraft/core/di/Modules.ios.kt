package me.androidbox.qrcraft.core.di

import me.androidbox.qrcraft.core.data.db.DatabaseFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {

        single { DatabaseFactory() }
    }