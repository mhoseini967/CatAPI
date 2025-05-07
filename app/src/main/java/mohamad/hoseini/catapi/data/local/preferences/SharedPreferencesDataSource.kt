package mohamad.hoseini.catapi.data.local.preferences
import android.content.SharedPreferences
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import mohamad.hoseini.catapi.domain.model.Settings

class SharedPreferencesDataSource(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) {

    companion object {
        const val SETTING_KEY = "SETTING_KEY"
    }

    val settingChangesFlow = MutableStateFlow(getSettings())

    fun saveSettings(value: Settings) {
        saveModel(SETTING_KEY, value)
        settingChangesFlow.tryEmit(value)
    }


    fun getSettings(): Settings {
        return getModel(SETTING_KEY, Settings())
    }


    private inline fun <reified T> getModel(key: String, default: T): T {
        val json = sharedPreferences.getString(key, null)
        return if (json != null) {
            try {
                gson.fromJson(json, T::class.java)
            } catch (e: Exception) {
                default
            }
        } else {
            default
        }
    }

    private inline fun <reified T> saveModel(key: String, model: T) {
        val json = gson.toJson(model)
        sharedPreferences.edit().putString(key, json).apply()
    }
}
