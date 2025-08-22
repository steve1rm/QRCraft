package me.androidbox.qrcraft.features.create_qr.choose_type.model

import me.androidbox.qrcraft.features.scan_result.domain.QRContentType

sealed class CreateQRDetail(
    val qrContentType: QRContentType,
) {
    data class Text(val text: String) : CreateQRDetail(QRContentType.TEXT)
    data class Link(val url: String) : CreateQRDetail(QRContentType.LINK)
    data class Contact(
        val name: String,
        val email: String,
        val phoneNumber: String,
    ) : CreateQRDetail(QRContentType.CONTACT)

    data class PhoneNumber(val phoneNumber: String) : CreateQRDetail(QRContentType.PHONE_NUMBER)
    data class Geolocation(
        val latitude: String,
        val longitude: String,
    ) : CreateQRDetail(QRContentType.GEOLOCATION)

    data class WiFi(
        val ssid: String,
        val password: String,
        val encryptionType: String,
    ) : CreateQRDetail(QRContentType.WIFI)

    companion object {
        val defaults = sequenceOf(
            Text(""),
            Link(""),
            Contact("", "", ""),
            PhoneNumber(""),
            Geolocation("", ""),
            WiFi("", "", ""),
        )
    }
}

fun CreateQRDetail.toQRCodeString(): String {
    return when (this) {
        is CreateQRDetail.Text -> text
        is CreateQRDetail.Link -> url
        is CreateQRDetail.PhoneNumber -> "tel:$phoneNumber"
        is CreateQRDetail.Geolocation -> "geo:$latitude,$longitude"
        is CreateQRDetail.Contact -> buildString {
            appendLine("BEGIN:VCARD")
            appendLine("VERSION:3.0")
            appendLine("N:$name")
            appendLine("TEL:$phoneNumber")
            appendLine("EMAIL:$email")
            appendLine("END:VCARD")
        }
        is CreateQRDetail.WiFi -> "WIFI:T:$encryptionType;S:$ssid;P:$password;;"
    }
}
