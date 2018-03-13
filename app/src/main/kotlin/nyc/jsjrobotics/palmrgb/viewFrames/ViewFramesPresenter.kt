package nyc.jsjrobotics.palmrgb.viewFrames

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import nyc.jsjrobotics.palmrgb.database.AppDatabase
import nyc.jsjrobotics.palmrgb.executeInThread
import nyc.jsjrobotics.palmrgb.runOnMainThread
import javax.inject.Inject

class ViewFramesPresenter @Inject constructor(val appDatabase: AppDatabase): LifecycleObserver {
    private lateinit var view: ViewFramesView
    private val disposables : CompositeDisposable = CompositeDisposable()

    fun init(view: ViewFramesView, onHiddenChanged: Observable<Boolean>) {
        this.view = view
        val hiddenDisposable = onHiddenChanged.subscribe { refreshView() }
        disposables.add(hiddenDisposable)
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
