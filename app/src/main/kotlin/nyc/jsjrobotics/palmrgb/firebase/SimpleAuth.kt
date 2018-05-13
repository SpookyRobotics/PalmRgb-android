package nyc.jsjrobotics.palmrgb.firebase

import nyc.jsjrobotics.palmrgb.globalState.SharedPreferencesManager
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SimpleAuth @Inject constructor(val sharedPreferencesManager: SharedPreferencesManager) {

    var currentUserId: String = setUserId()

    private fun setUserId(): String {
        var userId = sharedPreferencesManager.getCurrentUserId()
        if (userId == null) {
            userId = "User-${UUID.randomUUID().mostSignificantBits}"
            sharedPreferencesManager.setCurrentUserId(userId)
        }
        return userId
    }
}