package mohamad.hoseini.catapi.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import mohamad.hoseini.catapi.R


@Composable
fun CatAPITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !darkTheme

    systemUiController.setStatusBarColor(
        color = colorResource(id = if (darkTheme) R.color.darkBackgroundColor else R.color.backgroundColor),
        darkIcons = useDarkIcons
    )

    systemUiController.setNavigationBarColor(
        color = colorResource(id = if (darkTheme) R.color.darkBackgroundColor else R.color.backgroundColor),
        darkIcons = useDarkIcons
    )

    val colorScheme = when {
        darkTheme -> darkColorScheme(
            primary = colorResource(id = R.color.primaryColor),
            secondary = colorResource(id = R.color.secondaryColor),
            background = colorResource(id = R.color.darkBackgroundColor),
            surface = colorResource(id = R.color.darkSurfaceColor),
            error = colorResource(id = R.color.errorColor),
            onPrimary = colorResource(id = R.color.onPrimaryColor),
            onError = colorResource(id = R.color.onErrorColor),
            onBackground = colorResource(id = R.color.white),
            onSurface = colorResource(id = R.color.white)
        )

        else -> lightColorScheme(
            primary = colorResource(id = R.color.primaryColor),
            secondary = colorResource(id = R.color.secondaryColor),
            background = colorResource(id = R.color.backgroundColor),
            surface = colorResource(id = R.color.surfaceColor),
            error = colorResource(id = R.color.errorColor),
            onPrimary = colorResource(id = R.color.onPrimaryColor),
            onError = colorResource(id = R.color.onErrorColor),
            onBackground = colorResource(id = R.color.onBackgroundColor),
            onSurface = colorResource(id = R.color.onSurfaceColor)
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}