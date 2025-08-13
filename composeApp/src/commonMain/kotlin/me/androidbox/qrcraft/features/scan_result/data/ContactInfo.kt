package me.androidbox.qrcraft.features.scan_result.data

data class ContactInfo(
    val name: String? = null,
    val phone: String? = null,
    val email: String? = null
) {

    override fun toString(): String {
        return listOfNotNull(name, phone, email)
            .joinToString("\n")
    }
}