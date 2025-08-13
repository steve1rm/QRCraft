package me.androidbox.qrcraft.features.scan_result.domain

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import qrcraft.composeapp.generated.resources.Res
import qrcraft.composeapp.generated.resources.scan_result
import qrcraft.composeapp.generated.resources.type_contact
import qrcraft.composeapp.generated.resources.type_geolocation
import qrcraft.composeapp.generated.resources.type_link
import qrcraft.composeapp.generated.resources.type_phone_number
import qrcraft.composeapp.generated.resources.type_text
import qrcraft.composeapp.generated.resources.type_wifi

enum class QRContentType {
    LINK, CONTACT, PHONE_NUMBER, GEOLOCATION, WIFI, TEXT, UNDEFINED
}

@Composable
fun QRContentType.toDisplayName(): String = when (this) {
    QRContentType.LINK -> stringResource(Res.string.type_link)
    QRContentType.CONTACT -> stringResource(Res.string.type_contact)
    QRContentType.PHONE_NUMBER -> stringResource(Res.string.type_phone_number)
    QRContentType.GEOLOCATION -> stringResource(Res.string.type_geolocation)
    QRContentType.WIFI -> stringResource(Res.string.type_wifi)
    QRContentType.TEXT -> stringResource(Res.string.type_text)
    QRContentType.UNDEFINED -> stringResource(Res.string.scan_result)
}