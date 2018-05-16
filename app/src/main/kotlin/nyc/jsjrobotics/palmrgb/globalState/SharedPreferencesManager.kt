package nyc.jsjrobotics.palmrgb.globalState

import android.content.Context
import nyc.jsjrobotics.palmrgb.Application
import javax.inject.Inject

/***
 * Commits are performed immediately
 */
class SharedPreferencesManager @Inject constructor(val application: Application) {
    companion object {
        val DEFAULT_SETTINGS = "DEFAULT"
        val LIVE_UPDATES = "live_updates"
        val CURRENT_USER_ID = "current_user_id"
    }

    private val sharedPreferenceFile = application.getSharedPreferences(DEFAULT_SETTINGS, Context.MODE_PRIVATE)

    fun getSendLiveUpdatesToHardware() : Boolean {
        return sharedPreferenceFile.getBoolean(LIVE_UPDATES, false)
    }

    fun setSendLiveUpdatesToHardware(liveUpdatesEnabled: Boolean) {
        sharedPreferenceFile.edit().putBoolean(LIVE_UPDATES, liveUpdatesEnabled).commit()
    }

    fun getCurrentUserId() : String? {
        return sharedPreferenceFile.getString(CURRENT_USER_ID, null)
    }

    fun setCurrentUserId(userId: String) {
        sharedPreferenceFile.edit().putString(CURRENT_USER_ID, userId).commit()
    }
}
