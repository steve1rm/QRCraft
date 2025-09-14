package me.androidbox.qrcraft.features.scan_result.data

data class ContactInfo(
    val name: String,
    val phone: String,
    val email: String
) {

    override fun toString(): String {
        return listOfNotNull(name, phone, email)
            .joinToString("\n")
    }
}