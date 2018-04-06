package nyc.jsjrobotics.palmrgb.androidInterfaces

import android.os.Bundle
import android.support.v4.app.Fragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.Application
import java.net.IDN
import javax.inject.Inject

/***
 * A fragment that injects dependencies before onCreate
 */
abstract class DefaultFragment : Fragment(), IDefaultFragment {

    private val hiddenChanged : PublishSubject<Boolean> = PublishSubject.create()
    val onHiddenChanged : Observable<Boolean> = hiddenChanged

    @Inject
    lateinit var navigationBarSettings : NavigationBarSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        Application.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        hiddenChanged.onNext(hidden)
        if (!hidden) {
            val hideNavigationBar = navigationBarSettings.HIDE_NAVIGATION_BAR.map { it.tag }.contains(tag)
            (activity as IDefaultActivity).showNavigationBar(!hideNavigationBar)
        }
    }

    override fun fragment() = this


}
