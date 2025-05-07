package mohamad.hoseini.catapi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dagger.hilt.android.AndroidEntryPoint
import mohamad.hoseini.catapi.data.local.preferences.SharedPreferencesDataSource
import mohamad.hoseini.catapi.ui.navigation.CatNavHost
import mohamad.hoseini.catapi.ui.theme.CatAPITheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPreferencesDataSource: SharedPreferencesDataSource
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val settings by sharedPreferencesDataSource.settingChangesFlow.collectAsState()
            CatAPITheme(darkTheme = settings.isDarkMode) {
                CatNavHost()
            }
        }
    }
}
