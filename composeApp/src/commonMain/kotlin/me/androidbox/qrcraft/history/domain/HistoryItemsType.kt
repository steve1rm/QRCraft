package me.androidbox.qrcraft.history.domain

import org.jetbrains.compose.resources.DrawableResource
import qrcraft.composeapp.generated.resources.Res
import qrcraft.composeapp.generated.resources.contact
import qrcraft.composeapp.generated.resources.geolocation
import qrcraft.composeapp.generated.resources.link
import qrcraft.composeapp.generated.resources.phone
import qrcraft.composeapp.generated.resources.text
import qrcraft.composeapp.generated.resources.wifi

enum class HistoryItemsType(val title: String, val image: DrawableResource) {
    LINK(title = "Link", image = Res.drawable.link),
    TEXT(title = "Text", image = Res.drawable.text),
    GEOLOCATION(title = "Geolocation", image = Res.drawable.geolocation),
    CONTACT(title = "Contact", image = Res.drawable.contact),
    WIFI(title = "Wi-Fi", image = Res.drawable.wifi),
    PHONE(title = "Phone Number", image = Res.drawable.phone)
}