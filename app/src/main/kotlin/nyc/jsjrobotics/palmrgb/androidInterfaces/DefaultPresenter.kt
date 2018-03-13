package nyc.jsjrobotics.palmrgb.androidInterfaces

import android.arch.lifecycle.LifecycleObserver
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.androidInterfaces.IDefaultActivity
import nyc.jsjrobotics.palmrgb.androidInterfaces.IDefaultFragment

abstract class DefaultPresenter : LifecycleObserver {
    // Only presenters should call on next to send requests for activity / fragment
    protected val activityNeeded: PublishSubject<Consumer<IDefaultActivity>> = PublishSubject.create()
    protected val fragmentNeeded: PublishSubject<Consumer<IDefaultFragment>> = PublishSubject.create()

    fun activityNeeded() : Observable<Consumer<IDefaultActivity>> = activityNeeded
    fun fragmentNeeded(): Observable<Consumer<IDefaultFragment>> = fragmentNeeded
}
