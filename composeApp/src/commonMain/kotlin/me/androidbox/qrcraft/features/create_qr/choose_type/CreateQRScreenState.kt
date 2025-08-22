package me.androidbox.qrcraft.features.create_qr.choose_type

import me.androidbox.qrcraft.features.create_qr.choose_type.model.CreateQRDetail

data class CreateQRScreenState(
    val createQRDetail: CreateQRDetail = CreateQRDetail.Text(""),
    val generateButtonEnabled: Boolean = false,
)