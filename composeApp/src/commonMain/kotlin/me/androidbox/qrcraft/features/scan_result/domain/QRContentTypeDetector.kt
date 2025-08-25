package me.androidbox.qrcraft.features.scan_result.domain


fun detectQRContentType(scannedQrCode: String): QRContentType {
    val trimmedQRCode = scannedQrCode.trim()

    return  when{
        trimmedQRCode.startsWith("BEGIN:VCARD", ignoreCase = true) -> QRContentType.CONTACT
        trimmedQRCode.startsWith("geo:", ignoreCase = true) -> QRContentType.GEOLOCATION
        trimmedQRCode.startsWith("tel:", ignoreCase = true) -> QRContentType.PHONE_NUMBER
        trimmedQRCode.matches(Regex("""^\+?\d{7,15}$""")) -> QRContentType.PHONE_NUMBER
        trimmedQRCode.startsWith("WIFI:", ignoreCase = true)-> QRContentType.WIFI
        trimmedQRCode.startsWith("http://", ignoreCase = true) || trimmedQRCode.startsWith("https://", ignoreCase = true) -> QRContentType.LINK
        else -> QRContentType.TEXT
    }
}