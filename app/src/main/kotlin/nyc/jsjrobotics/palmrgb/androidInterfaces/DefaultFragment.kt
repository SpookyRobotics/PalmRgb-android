package nyc.jsjrobotics.palmrgb.androidInterfaces

import android.os.Bundle
import android.support.v4.app.Fragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.Application

/***
 * A fragment that injects dependencies before onCreate
 */
abstract class DefaultFragment : Fragment() {

    private val hiddenChanged : PublishSubject<Boolean> = PublishSubject.create()
    val onHiddenChanged : Observable<Boolean> = hiddenChanged

    override fun onCreate(savedInstanceState: Bundle?) {
        Application.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        hiddenChanged.onNext(hidden)
    }


}
