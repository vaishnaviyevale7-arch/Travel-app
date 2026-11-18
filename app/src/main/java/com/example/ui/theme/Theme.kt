package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Immutable
data class TerracottaExtendedColors(
    val sandCanvas: Color,
    val travertineWell: Color,
    val linenCard: Color,
    val slateBody: Color,
    val slateMuted: Color,
    val coralAccent: Color,
    val tidepoolTeal: Color,
    val tealTint: Color,
    val terracottaTint: Color,
    val cardHairline: Color,
    val inputHairline: Color,
    val floatingBarSurface: Color,
    val isDark: Boolean
)

private val LightExtendedColors = TerracottaExtendedColors(
    sandCanvas = WarmSandCanvas,
    travertineWell = SoftTravertine,
    linenCard = PureLinenWhite,
    slateBody = SlateBody,
    slateMuted = SlateMuted,
    coralAccent = CoralAccent,
    tidepoolTeal = TidepoolTeal,
    tealTint = TidepoolTealTint,
    terracottaTint = TerracottaTint,
    cardHairline = CardHairline,
    inputHairline = InputHairline,
    floatingBarSurface = FrostedSandBar,
    isDark = false
)

private val DarkExtendedColors = TerracottaExtendedColors(
    sandCanvas = NightTideCanvas,
    travertineWell = NightTideSurface,
    linenCard = NightTideElevated,
    slateBody = NightTideSandText,
    slateMuted = NightTideMutedText,
    coralAccent = NightTideTerracotta,
    tidepoolTeal = NightTideTeal,
    tealTint = Color(0x263FD2C4),
    terracottaTint = Color(0x26F27658),
    cardHairline = NightTideHairline,
    inputHairline = NightTideHairline,
    floatingBarSurface = FrostedNightBar,
    isDark = true
)

val LocalTerracottaColors = staticCompositionLocalOf { LightExtendedColors }

private val TerracottaLightColorScheme = lightColorScheme(
    primary = TerracottaPrimary,
    onPrimary = Color.White,
    primaryContainer = CoralAccent,
    onPrimaryContainer = Color.White,
    secondary = TidepoolTeal,
    onSecondary = Color.White,
    secondaryContainer = TidepoolTealContainer,
    onSecondaryContainer = TidepoolTealDeep,
    tertiary = TerracottaDeep,
    onTertiary = Color.White,
    background = WarmSandCanvas,
    onBackground = DeepSlate,
    surface = WarmSandCanvas,
    onSurface = DeepSlate,
    surfaceVariant = SoftTravertine,
    onSurfaceVariant = SlateBody,
    surfaceContainerLowest = PureLinenWhite,
    surfaceContainerLow = SoftTravertine,
    surfaceContainer = SoftTravertine,
    outline = SlateMuted,
    outlineVariant = InputHairline,
    error = ErrorCrimson,
    onError = Color.White,
    errorContainer = ErrorContainerLight
)

private val TerracottaDarkColorScheme = darkColorScheme(
    primary = NightTideTerracotta,
    onPrimary = NightTideCanvas,
    primaryContainer = TerracottaPrimary,
    onPrimaryContainer = Color.White,
    secondary = NightTideTeal,
    onSecondary = NightTideCanvas,
    secondaryContainer = TidepoolTealDeep,
    onSecondaryContainer = TidepoolTealContainer,
    tertiary = CoralAccent,
    onTertiary = Color.White,
    background = NightTideCanvas,
    onBackground = NightTideSandText,
    surface = NightTideCanvas,
    onSurface = NightTideSandText,
    surfaceVariant = NightTideSurface,
    onSurfaceVariant = NightTideMutedText,
    surfaceContainerLowest = NightTideElevated,
    surfaceContainerLow = NightTideSurface,
    surfaceContainer = NightTideSurface,
    outline = NightTideMutedText,
    outlineVariant = NightTideHairline,
    error = ErrorCrimson,
    onError = Color.White
)

val TerracottaShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

object TerracottaTheme {
    val colors: TerracottaExtendedColors
        @Composable get() = LocalTerracottaColors.current
}

@Composable
fun TerracottaTideTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) TerracottaDarkColorScheme else TerracottaLightColorScheme
    val extendedColors = if (darkTheme) DarkExtendedColors else LightExtendedColors

    CompositionLocalProvider(LocalTerracottaColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = TerracottaTypography,
            shapes = TerracottaShapes,
            content = content
        )
    }
}
