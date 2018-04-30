package nyc.jsjrobotics.palmrgb.fragments.connectionStatus

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.Observable
import nyc.jsjrobotics.palmrgb.androidInterfaces.DefaultPresenter
import nyc.jsjrobotics.palmrgb.runOnMainThread
import javax.inject.Inject

class ConnectionStatusPresenter @Inject constructor(val model : ConnectionStatusModel) : DefaultPresenter() {
    lateinit var view: ConnectionStatusView

    fun init(view: ConnectionStatusView, onHiddenChanged: Observable<Boolean>) {
        this.view = view
        view.displayConnected(false)
        model.onConnectionStatusChanged.subscribe(this::updateConnectedDisplay)
        model.updateConnectionStatus()
        onHiddenChanged.subscribe { hidden ->
            if (!hidden) {
                model.updateConnectionStatus()
            }
        }
    }

    private fun updateConnectedDisplay(isConnected : Boolean) {
        runOnMainThread {
            view.displayConnected(isConnected)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun registerUpdateConnectionReceiver() {
        activityNeeded.onNext(model::registerUpdateConnectionReceiver)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun checkConnectionOnResume() {
        model.updateConnectionStatus()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun unregisterUpdateConnectionReceiver() {
        activityNeeded.onNext(model::unregisterUpdateConnectionReceiver)
    }

}
