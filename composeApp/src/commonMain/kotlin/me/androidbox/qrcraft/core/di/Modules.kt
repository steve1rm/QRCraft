package me.androidbox.qrcraft.core.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import me.androidbox.qrcraft.core.data.db.AppDatabase
import me.androidbox.qrcraft.core.data.db.DatabaseFactory
import me.androidbox.qrcraft.create.CreatePreviewViewModel
import me.androidbox.qrcraft.features.create_qr.choose_type.CreateQRScreenViewModel
import me.androidbox.qrcraft.features.scan_result.data.DefaultQREntryRepository
import me.androidbox.qrcraft.features.scan_result.domain.QRContentType
import me.androidbox.qrcraft.features.scan_result.domain.QREntryRepository
import me.androidbox.qrcraft.features.scan_result.presentation.QREntryViewModel
import me.androidbox.qrcraft.history.presentation.HistoryViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val qrModule = module {

    viewModel { (type: QRContentType) ->
        CreateQRScreenViewModel(type)
    }
    single<CoroutineScope> {
        CoroutineScope(Dispatchers.Default + SupervisorJob())
    }
}

expect val platformModule: Module

val sharedModule = module {

    single {
        get<DatabaseFactory>().create().setDriver(BundledSQLiteDriver())
            .fallbackToDestructiveMigration(true).build()
    }

    single {
        get<AppDatabase>().qrEntryDao
    }

    viewModelOf(::QREntryViewModel)
    viewModelOf(::HistoryViewModel)
    viewModelOf(::CreatePreviewViewModel)
    singleOf(::DefaultQREntryRepository).bind<QREntryRepository>()

}