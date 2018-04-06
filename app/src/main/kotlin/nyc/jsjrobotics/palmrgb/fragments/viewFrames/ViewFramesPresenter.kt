package nyc.jsjrobotics.palmrgb.fragments.viewFrames

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import nyc.jsjrobotics.palmrgb.androidInterfaces.DefaultPresenter
import nyc.jsjrobotics.palmrgb.androidInterfaces.FragmentId
import nyc.jsjrobotics.palmrgb.database.AppDatabase
import nyc.jsjrobotics.palmrgb.executeInThread
import nyc.jsjrobotics.palmrgb.runOnMainThread
import nyc.jsjrobotics.palmrgb.fragments.viewFrames.dialog.RgbFrameDialogFragment
import javax.inject.Inject

class ViewFramesPresenter @Inject constructor(val appDatabase: AppDatabase): DefaultPresenter() {
    private lateinit var view: ViewFramesView
    private val disposables : CompositeDisposable = CompositeDisposable()
    private val updateViewFramesReceiver : BroadcastReceiver = updateFramesBroadcastReceiver()

    companion object {
        private val UPDATE_VIEW_FRAMES = "MESSAGE_UPDATE_VIEW_FRAMES"
        fun updateViewFramesIntent() = Intent(UPDATE_VIEW_FRAMES)
    }
    private fun updateFramesBroadcastReceiver(): BroadcastReceiver {
        return object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                refreshView()
            }
        }
    }

    fun init(view: ViewFramesView, onHiddenChanged: Observable<Boolean>) {
        this.view = view
        val hiddenDisposable = onHiddenChanged.subscribe { hidden ->
            if (!hidden) {
                refreshView()
            }
        }
        val frameSelectedDisposable = view.onFrameSelected().subscribe { frameSelected(it) }
        disposables.addAll(hiddenDisposable, frameSelectedDisposable)
    }

    private fun frameSelected(frameId: Long) {
        activityNeeded.onNext( { activity ->
            val fragmentManager = activity.getActivity().supportFragmentManager
            val fragmentId = FragmentId.RGB_FRAME_DIALOG_FRAGMENT
            val arguments = RgbFrameDialogFragment.buildArguments(frameId)
            val fragment = fragmentId.supplier(arguments) as RgbFrameDialogFragment
            fragment.show(fragmentManager, fragmentId.tag)
        })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun registerUpdateViewFramesReceiver() {
        activityNeeded.onNext{activity ->
            val intentFilter = IntentFilter(UPDATE_VIEW_FRAMES)
            LocalBroadcastManager.getInstance(activity.applicationContext()).registerReceiver(updateViewFramesReceiver, intentFilter)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun unregisterUpdateViewFramesReceiver() {
        activityNeeded.onNext{activity ->
            LocalBroadcastManager.getInstance(activity.applicationContext()).unregisterReceiver(updateViewFramesReceiver)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun refreshView() {
        executeInThread {
            val savedFrames = appDatabase.rgbFramesDao().getAll()
            runOnMainThread {  view.setData(savedFrames) }

        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unsubscribe() {
        disposables.clear()
    }
}
