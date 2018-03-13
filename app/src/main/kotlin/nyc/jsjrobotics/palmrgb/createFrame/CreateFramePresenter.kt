package nyc.jsjrobotics.palmrgb.createFrame

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v4.app.FragmentManager
import io.reactivex.disposables.CompositeDisposable
import nyc.jsjrobotics.palmrgb.androidInterfaces.DefaultPresenter
import javax.inject.Inject

class CreateFramePresenter @Inject constructor(val dialogModel : CreateFrameDialogModel,
                                               val createFrameModel: CreateFrameModel) : DefaultPresenter() {
    private lateinit var view: CreateFrameView
    private val disposables : CompositeDisposable = CompositeDisposable()
    private var displayedDialog : CreateFrameDialog? = null

    fun init(fragmentManager : FragmentManager, view: CreateFrameView) {
        this.view = view
        disposables.clear()
        val createDialogSubscription = view.onCreateFrameClicked.subscribe {
            displayDialog(fragmentManager)
        }
        val saveFrameSubscription = dialogModel.onSaveFrameRequested.subscribe { frameTitle ->
            createFrameModel.writeCurrentFrameToDatabase(frameTitle)
        }
        disposables.addAll(createDialogSubscription, saveFrameSubscription)
    }

    private fun displayDialog(fragmentManager: FragmentManager) {
        displayedDialog?.dismiss()
        val dialog = CreateFrameDialog()
        dialog.show(fragmentManager, CreateFrameDialog.TAG)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unsubscribe() {
        disposables.clear()
    }

}