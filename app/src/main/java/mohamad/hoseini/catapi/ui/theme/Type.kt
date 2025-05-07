package mohamad.hoseini.catapi.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import mohamad.hoseini.catapi.R

val lightFont = FontFamily(Font(R.font.poppins_light))
val regularFont = FontFamily(Font(R.font.poppins_regular))
val mediumFont = FontFamily(Font(R.font.poppins_medium))
val boldFont = FontFamily(Font(R.font.poppins_bold))

val defaultType = TextStyle(
    fontFamily = regularFont,
    fontWeight = FontWeight.Normal,
    fontSize = 13.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)

val Title = TextStyle(
    fontFamily = mediumFont,
    fontSize = 15.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.5.sp,
)

val HeaderTitle = TextStyle(
    fontFamily = mediumFont,
    fontSize = 24.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp,
)
val HeaderDescription = TextStyle(
    fontFamily = regularFont,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp,
    color = Color(0xff8992A9)

)

val ButtonText = TextStyle(
    fontFamily = boldFont,
    fontSize = 15.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp,
    color = Color.White
)

val TextInputLabelText = TextStyle(
    fontFamily = mediumFont,
    fontSize = 13.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)

val ErrorText = TextStyle(
    fontFamily = lightFont,
    fontSize = 13.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = defaultType,
    titleSmall = defaultType,
    bodySmall = defaultType,
    bodyMedium = defaultType,
    displayLarge = defaultType,
    displaySmall = defaultType,
    displayMedium = defaultType,
    headlineLarge = defaultType,
    headlineMedium = defaultType,
    headlineSmall = defaultType,
    labelLarge = defaultType,
    labelMedium = defaultType,
    titleMedium = defaultType,
    labelSmall = defaultType,
    titleLarge = defaultType
)