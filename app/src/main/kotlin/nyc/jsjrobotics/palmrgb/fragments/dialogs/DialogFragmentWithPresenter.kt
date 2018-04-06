package nyc.jsjrobotics.palmrgb.fragments.dialogs

import nyc.jsjrobotics.palmrgb.androidInterfaces.DefaultPresenter
import nyc.jsjrobotics.palmrgb.androidInterfaces.HasPresenter
import nyc.jsjrobotics.palmrgb.androidInterfaces.PresenterManager

abstract class DialogFragmentWithPresenter : DefaultDialogFragment() , HasPresenter {
    val presenterManager : PresenterManager = PresenterManager()

    override fun setPresenter(presenter: DefaultPresenter)  = presenterManager.setPresenter(presenter, this)


    override fun subscribeToPresenter(presenter: DefaultPresenter) = presenterManager.subscribeToPresenter(presenter, this)


    override fun onDestroy() {
        presenterManager.onDestroy()
        super.onDestroy()
    }
}
