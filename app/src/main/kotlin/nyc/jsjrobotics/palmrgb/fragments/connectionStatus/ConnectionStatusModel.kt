package nyc.jsjrobotics.palmrgb.fragments.connectionStatus

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.Application
import nyc.jsjrobotics.palmrgb.androidInterfaces.IDefaultActivity
import nyc.jsjrobotics.palmrgb.service.HardwareState
import nyc.jsjrobotics.palmrgb.service.remoteInterface.HackdayLightsBackend
import nyc.jsjrobotics.palmrgb.service.remoteInterface.HackdayLightsInterface
import nyc.jsjrobotics.palmrgb.service.remoteInterface.RequestType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectionStatusModel @Inject constructor(val application: Application) {
    private val hardwareState: HardwareState = HardwareState()
    private val connectionStatusChanged: PublishSubject<Boolean> = PublishSubject.create()
    val onConnectionStatusChanged: Observable<Boolean> = connectionStatusChanged
    private val updateConnectionStatus: BroadcastReceiver = buildUpdateReceiver()


    fun updateConnectionStatus() {
        HackdayLightsInterface.startRequest(RequestType.CHECK_CONNECTION)
    }

    fun cachedConnectionStatus(): Boolean{
        return hardwareState.isConnected
    }


    private fun buildUpdateReceiver() : BroadcastReceiver {
        return object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                if (intent.hasExtra(HackdayLightsBackend.CONNECTION_CHECK_RESPONSE)) {
                    val isConnected = intent.getBooleanExtra(HackdayLightsBackend.CONNECTION_CHECK_RESPONSE, false)
                    hardwareState.isConnected = isConnected
                    connectionStatusChanged.onNext(isConnected)
                }
            }

        }
    }

    fun registerUpdateConnectionReceiver(activity: IDefaultActivity) {
        val intentFilter = IntentFilter(HackdayLightsBackend.CONNECTION_CHECK_RESPONSE)
        LocalBroadcastManager.getInstance(activity.applicationContext()).registerReceiver(updateConnectionStatus, intentFilter)
    }

    fun unregisterUpdateConnectionReceiver(activity: IDefaultActivity) {
        LocalBroadcastManager.getInstance(activity.applicationContext()).unregisterReceiver(updateConnectionStatus)
    }
}
