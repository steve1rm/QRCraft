package me.androidbox.qrcraft.core.di

import me.androidbox.qrcraft.features.create_qr.choose_type.CreateQRScreenViewModel
import me.androidbox.qrcraft.features.scan_result.domain.QRContentType
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val qrModule = module {

    viewModel { (type : QRContentType) ->
        CreateQRScreenViewModel(type)
    }
}