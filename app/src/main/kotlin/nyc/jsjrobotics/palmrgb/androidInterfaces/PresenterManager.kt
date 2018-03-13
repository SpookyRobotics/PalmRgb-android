package nyc.jsjrobotics.palmrgb.androidInterfaces

import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable


class PresenterManager {
    private val disposables : CompositeDisposable = CompositeDisposable()

    private var onDestroyAction: (() -> Unit)? = null
    private var presenterIsSet: Boolean = false

    fun setPresenter(presenter: DefaultPresenter, fragment: Fragment) {
        if (presenterIsSet) {
            throw IllegalStateException("Presenter is already set")
        }
        presenterIsSet = true
        subscribeToPresenter(presenter, fragment)
        val lifecycle = fragment.lifecycle
        lifecycle.addObserver(presenter)
        onDestroyAction = { lifecycle.removeObserver(presenter) }
    }

    fun subscribeToPresenter(presenter: DefaultPresenter, fragment : Fragment) {
        val activityAction = presenter.activityNeeded().subscribe { it.accept(fragment.activity as IDefaultActivity) }
        val fragmentAction = presenter.fragmentNeeded().subscribe { it.accept(fragment as IDefaultFragment) }
        disposables.addAll(activityAction, fragmentAction)
    }

    fun onDestroy() {
        onDestroyAction?.invoke()
        onDestroyAction = null
        disposables.dispose()
    }
}