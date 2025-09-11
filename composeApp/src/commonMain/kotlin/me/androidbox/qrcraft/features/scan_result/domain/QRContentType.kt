package me.androidbox.qrcraft.features.scan_result.domain

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource
import qrcraft.composeapp.generated.resources.Res
import qrcraft.composeapp.generated.resources.contact
import qrcraft.composeapp.generated.resources.geolocation
import qrcraft.composeapp.generated.resources.link
import qrcraft.composeapp.generated.resources.phone
import qrcraft.composeapp.generated.resources.scan_result
import qrcraft.composeapp.generated.resources.text
import qrcraft.composeapp.generated.resources.type_contact
import qrcraft.composeapp.generated.resources.type_geolocation
import qrcraft.composeapp.generated.resources.type_link
import qrcraft.composeapp.generated.resources.type_phone_number
import qrcraft.composeapp.generated.resources.type_text
import qrcraft.composeapp.generated.resources.type_wifi
import qrcraft.composeapp.generated.resources.wifi

enum class QRContentType {
    TEXT, LINK, CONTACT, PHONE_NUMBER, GEOLOCATION, WIFI, UNDEFINED;

    companion object {
        val validEntries = entries.filterNot { it == UNDEFINED }
    }
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

fun QRContentType.toSvgResource(): String = when (this) {
    QRContentType.LINK -> "files/link.svg"
    QRContentType.CONTACT -> "files/contact.svg"
    QRContentType.PHONE_NUMBER -> "files/phone-number.svg"
    QRContentType.GEOLOCATION -> "files/geolocation.svg"
    QRContentType.WIFI -> "files/wifi.svg"
    QRContentType.TEXT -> "files/text.svg"
    QRContentType.UNDEFINED -> "files/link.svg"
}


fun QRContentType.toDrawableResource(): DrawableResource = when (this) {
    QRContentType.LINK -> Res.drawable.link
    QRContentType.CONTACT -> Res.drawable.contact
    QRContentType.PHONE_NUMBER -> Res.drawable.phone
    QRContentType.GEOLOCATION -> Res.drawable.geolocation
    QRContentType.WIFI -> Res.drawable.wifi
    QRContentType.TEXT -> Res.drawable.text
    QRContentType.UNDEFINED -> Res.drawable.text
}



