package nyc.jsjrobotics.palmrgb.androidInterfaces


/**
 * Default Fragment with disposables and settable presenter. Disposables are disposed of in onDestroy
 * A Fragment may only have one presenter.
 * You must call set presenter for activityNeeded, fragmentNeeded callbacks to be triggered
 */
abstract class FragmentWithPresenter : DefaultFragment(), HasPresenter {
    val presenterManager : PresenterManager = PresenterManager()

    override fun setPresenter(presenter: DefaultPresenter)  = presenterManager.setPresenter(presenter, this)


    override fun subscribeToPresenter(presenter: DefaultPresenter) = presenterManager.subscribeToPresenter(presenter, this)

    override fun finish() {
        activity?.finish()
    }

    override fun onDestroy() {
        presenterManager.onDestroy()
        super.onDestroy()
    }
}