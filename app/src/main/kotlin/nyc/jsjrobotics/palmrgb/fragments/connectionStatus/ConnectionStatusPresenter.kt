package nyc.jsjrobotics.palmrgb.fragments.connectionStatus

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import nyc.jsjrobotics.palmrgb.androidInterfaces.DefaultPresenter
import nyc.jsjrobotics.palmrgb.fragments.createFrame.CreateFrameModel
import nyc.jsjrobotics.palmrgb.globalState.SharedPreferencesManager
import nyc.jsjrobotics.palmrgb.runOnMainThread
import nyc.jsjrobotics.palmrgb.service.remoteInterface.HackdayLightsInterface
import javax.inject.Inject

class ConnectionStatusPresenter @Inject constructor(val model : ConnectionStatusModel,
                                                    val createFrameModel: CreateFrameModel,
                                                    val sharedPreferencesManager: SharedPreferencesManager) : DefaultPresenter() {
    lateinit var view: ConnectionStatusView

    fun init(view: ConnectionStatusView, onHiddenChanged: Observable<Boolean>) {
        this.view = view

        subscribeFragmentVisible(onHiddenChanged)
        subscribeConnectClicked()
        subscribeConnectionActionSelected()
        subscribeDisconnectClicked()
        subscribeConnectionStatusChanged()
        subscribeLiveUpdatesChanged()
        model.updateConnectionStatus()
        view.displayConnected(false)


    }

    private fun subscribeLiveUpdatesChanged() {
        val liveUpdatesDisposable = view.onLiveUpdatesClicked.subscribe {liveUpdatesEnabled ->
            sharedPreferencesManager.setSendLiveUpdatesToHardware(liveUpdatesEnabled)
            if (liveUpdatesEnabled) {
                createFrameModel.updateRemoteDisplay()
            }
        }
        disposables.add(liveUpdatesDisposable)
    }

    private fun subscribeConnectionStatusChanged() {
        val connectionDisposable = model.onConnectionStatusChanged.subscribe(this::updateConnectedDisplay)
        disposables.add(connectionDisposable)
    }

    private fun subscribeDisconnectClicked() {
        val disconnectDisposable = view.onDisconnectClicked.subscribe {
            model.disconnect()
        }
        disposables.add(disconnectDisposable)
    }

    private fun subscribeConnectionActionSelected() {
        val requestSelectedDisposable = view.onConnectedActionSelected().subscribe {
            HackdayLightsInterface.startRequest(it)
        }
        disposables.add(requestSelectedDisposable)
    }

    private fun subscribeConnectClicked() {
        val connectDisposable = view.onConnectClicked.subscribe {
            runOnMainThread {
                view.displayCheckingConnection()
            }
            model.setUrl(it)
            model.updateConnectionStatus()
        }
        disposables.add(connectDisposable)
    }

    private val disposables: CompositeDisposable = CompositeDisposable()

    private fun subscribeFragmentVisible(onHiddenChanged: Observable<Boolean>) {
        val hiddenChanged = onHiddenChanged.subscribe { hidden ->
            if (!hidden) {
                model.updateConnectionStatus()
            }
        }
        disposables.add(hiddenChanged)
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
