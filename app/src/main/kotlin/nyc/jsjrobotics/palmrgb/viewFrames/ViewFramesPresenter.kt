package nyc.jsjrobotics.palmrgb.viewFrames

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import nyc.jsjrobotics.palmrgb.androidInterfaces.DefaultPresenter
import nyc.jsjrobotics.palmrgb.androidInterfaces.FragmentId
import nyc.jsjrobotics.palmrgb.database.AppDatabase
import nyc.jsjrobotics.palmrgb.executeInThread
import nyc.jsjrobotics.palmrgb.runOnMainThread
import nyc.jsjrobotics.palmrgb.viewFrames.dialog.RgbFrameDialogFragment
import javax.inject.Inject

class ViewFramesPresenter @Inject constructor(val appDatabase: AppDatabase): DefaultPresenter() {
    private lateinit var view: ViewFramesView
    private val disposables : CompositeDisposable = CompositeDisposable()

    fun init(view: ViewFramesView, onHiddenChanged: Observable<Boolean>) {
        this.view = view
        val hiddenDisposable = onHiddenChanged.subscribe { refreshView() }
        val frameSelectedDisposable = view.onFrameSelected().subscribe { frameSelected(it) }
        disposables.addAll(hiddenDisposable, frameSelectedDisposable)
    }

    private fun frameSelected(frameName: String) {
        activityNeeded.onNext( Consumer { activity ->
            val fragmentManager = activity.getActivity().supportFragmentManager
            val fragmentId = FragmentId.RGB_FRAME_DIALOG_FRAGMENT
            val arguments = RgbFrameDialogFragment.buildArguments(frameName)
            val fragment = fragmentId.supplier(arguments) as RgbFrameDialogFragment
            fragment.show(fragmentManager, fragmentId.tag)
        })
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun refreshView() {
        executeInThread {
            val savedFrames = appDatabase.rgbFramesDao().getAll()
            runOnMainThread ( Runnable{  view.setData(savedFrames) } )

        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unsubscribe() {
        disposables.clear()
    }
}
