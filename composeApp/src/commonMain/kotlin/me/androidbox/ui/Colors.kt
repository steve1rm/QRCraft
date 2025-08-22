package me.androidbox.ui

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Base UI Colors
val Primary = Color(0xFFEBFF69)
val Surface = Color(0xFFEDF2F5)
val SurfaceHigher = Color(0xFFFFFFFF)
val OnSurface = Color(0xFF273037)
val OnSurfaceAlt = Color(0xFF505F6A)
val Overlay = Color(0x80000000) // 50% Black
val OnOverlay = Color(0xFFFFFFFF)
val OnSurfaceDisabled = Color(0xFF8C99A2)

val ColorScheme.bgGradient: Brush
    get() = Brush.linearGradient(
        listOf(
            Color(0xff58A1F8),
            Color(0xff5A4CF7)
        )
    )

// Link
val Link = Color(0xFF373F05)
val LinkBG = Color(0x4DEBFF69) // 30% Primary

// States
val Error = Color(0xFFF12244)
val Success = Color(0xFF4DDA9D)

// Text
val Text = Color(0xFF583DC5)
val TextBG = Color(0x1A583DC5) // 10%

// Contact
val Contact = Color(0xFF259570)
val ContactBG = Color(0x1A259570) // 10%

// Geo
val Geo = Color(0xFFB51D5C)
val GeoBG = Color(0x1AB51D5C) // 10%

// Phone
val Phone = Color(0xFFC86017)
val PhoneBG = Color(0x1AC86017) // 10%

// WiFi
val WiFi = Color(0xFF1F44CD)
val WiFiBG = Color(0x1A1F44CD) // 10%