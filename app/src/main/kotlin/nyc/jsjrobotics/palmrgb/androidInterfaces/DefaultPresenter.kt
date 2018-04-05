package nyc.jsjrobotics.palmrgb.androidInterfaces

import android.arch.lifecycle.LifecycleObserver
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject

abstract class DefaultPresenter : LifecycleObserver {
    // Only presenters should call on next to send requests for activity / fragment
    protected val activityNeeded: PublishSubject<(IDefaultActivity) -> Unit> = PublishSubject.create()
    protected val fragmentNeeded: PublishSubject<(IDefaultFragment) -> Unit> = PublishSubject.create()

    fun activityNeeded() : Observable<(IDefaultActivity) -> Unit> = activityNeeded
    fun fragmentNeeded(): Observable<(IDefaultFragment) -> Unit> = fragmentNeeded
}
