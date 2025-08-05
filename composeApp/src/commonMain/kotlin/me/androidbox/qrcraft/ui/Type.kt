package me.androidbox.qrcraft.ui

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import qrcraft.composeapp.generated.resources.Res
import qrcraft.composeapp.generated.resources.suse_medium
import qrcraft.composeapp.generated.resources.suse_regular
import qrcraft.composeapp.generated.resources.suse_semibold


@Composable
fun appTypography(): Typography {

    val susFontFamily = FontFamily(
        Font(Res.font.suse_regular, FontWeight.Normal),
        Font(Res.font.suse_medium, FontWeight.Medium),
        Font(Res.font.suse_semibold, FontWeight.SemiBold)
    )

    return Typography(
        titleMedium = TextStyle(
            fontFamily = susFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            lineHeight = 32.sp
        ),
        titleSmall = TextStyle(
            fontFamily = susFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 19.sp,
            lineHeight = 24.sp
        ),
        labelLarge = TextStyle(
            fontFamily = susFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 20.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = susFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 20.sp
        )
    )
}