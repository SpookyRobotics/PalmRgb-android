package nyc.jsjrobotics.palmrgb.createFrame

import android.support.v4.app.FragmentManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CreateFramePresenter @Inject constructor(val model : CreateFrameDialogModel) {
    private lateinit var view: CreateFrameView
    private val disposables : CompositeDisposable = CompositeDisposable()
    private var displayedDialog : CreateFrameDialog? = null

    fun init(fragmentManager : FragmentManager, view: CreateFrameView) {
        this.view = view
        disposables.clear()
        val createDialogSubscription = view.onCreateFrameClicked.subscribe {
            model.currentFrame = view.currentFrame()
            displayDialog(fragmentManager)
        }
        disposables.add(createDialogSubscription)
    }

    private fun displayDialog(fragmentManager: FragmentManager) {
        displayedDialog?.dismiss()
        val dialog = CreateFrameDialog()
        dialog.show(fragmentManager, CreateFrameDialog.TAG)
    }

}