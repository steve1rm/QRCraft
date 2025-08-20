package me.androidbox.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
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


private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnSurface,
    surface = Surface,
    onSurface = OnSurface,
    background = SurfaceHigher,
    onBackground = OnSurfaceAlt,
    error = Error,
    onError = SurfaceHigher
)

@Composable
fun appTypography(): Typography {

    val regularFontFamily = FontFamily(Font(Res.font.suse_regular))
    val mediumFontFamily = FontFamily(Font(Res.font.suse_medium))
    val semiBoldFontFamily = FontFamily(Font(Res.font.suse_semibold))

    return Typography(
        titleMedium = TextStyle(
            fontFamily = semiBoldFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            lineHeight = 32.sp
        ),
        titleSmall = TextStyle(
            fontFamily = semiBoldFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 19.sp,
            lineHeight = 24.sp
        ),
        labelLarge = TextStyle(
            fontFamily = mediumFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 20.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = regularFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 20.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = regularFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 16.sp
        )
    )
}

@Composable
fun AppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colors = LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = appTypography(),
        shapes = AppShapes,
        content = content
    )
}