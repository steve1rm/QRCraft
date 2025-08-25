package me.androidbox.qrcraft.features.scan_result.domain

import me.androidbox.qrcraft.features.scan_result.data.ContactInfo


fun extractQRContent(scannedQRCode: String, qrContentType: QRContentType): String {
    return when (qrContentType) {
        QRContentType.LINK -> scannedQRCode.trim()
        QRContentType.CONTACT -> {
            extractVCardInfo(scannedQRCode).toString()
        }

        QRContentType.PHONE_NUMBER -> {
            extractPhone(scannedQRCode = scannedQRCode)
        }

        QRContentType.TEXT -> {
            scannedQRCode
        }

        QRContentType.GEOLOCATION -> {
            extractGeoLocation(scannedQRCode = scannedQRCode)
        }

        QRContentType.WIFI -> {
            extractWifi(scannedQRCode = scannedQRCode) ?: ""
        }

        else -> ""
    }
}


fun extractVCardInfo(raw: String): ContactInfo? {
    val startIndex = raw.indexOf("BEGIN:VCARD", ignoreCase = true)
    val endIndex = raw.indexOf("END:VCARD", ignoreCase = true)

    if (startIndex == -1 || endIndex == -1 || endIndex <= startIndex) {
        return null
    }

    var name: String? = null
    var phone: String? = null
    var email: String? = null

    raw.substring(startIndex, endIndex)
        .lines()
        .map { it.trim() }
        .forEach { line ->
            when {
                line.startsWith("N:", ignoreCase = true) ->
                    name = line.removePrefix("N:").trim()

                line.startsWith("TEL:", ignoreCase = true) ->
                    phone = line.removePrefix("TEL:").trim()

                line.startsWith("EMAIL:", ignoreCase = true) ->
                    email = line.removePrefix("EMAIL:").trim()
            }
        }

    return ContactInfo(name, phone, email)
}


fun extractPhone(scannedQRCode: String): String {
    return scannedQRCode.removePrefix("tel:").trim()

}

fun extractGeoLocation(scannedQRCode: String): String {
    return scannedQRCode.removePrefix("geo:").trim()

}

fun extractWifi(scannedQRCode: String): String? {
    // Format: WIFI:S:<SSID>;T:<WPA|WEP|>;P:<password>;H:<true|false>;;
    val regex = Regex("""WIFI:S:(.*?);T:(.*?);P:(.*?);H:(.*?);;""", RegexOption.IGNORE_CASE)
    val match = regex.find(scannedQRCode.trim())

    return if (match != null) {
        val ssid = match.groupValues[1]
        val type = match.groupValues[2]
        val password = match.groupValues[3]
        val hidden = match.groupValues[4].equals("true", ignoreCase = true)

        buildString {
            appendLine("SSID: $ssid")
            appendLine("Password: $password")
            appendLine("Encryption type: $type")
            append("Hidden: ${if (hidden) "Yes" else "No"}")
        }
    } else null
}