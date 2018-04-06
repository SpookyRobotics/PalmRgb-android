package nyc.jsjrobotics.palmrgb.fragments.createFrame

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v4.app.FragmentManager
import io.reactivex.disposables.CompositeDisposable
import nyc.jsjrobotics.palmrgb.androidInterfaces.DefaultPresenter
import nyc.jsjrobotics.palmrgb.androidInterfaces.FragmentId
import nyc.jsjrobotics.palmrgb.fragments.dialogs.changeDisplay.ChangeDisplayDialog
import nyc.jsjrobotics.palmrgb.fragments.dialogs.changeDisplay.ChangeDisplayDialogModel
import nyc.jsjrobotics.palmrgb.fragments.dialogs.DialogFragmentWithPresenter
import nyc.jsjrobotics.palmrgb.fragments.dialogs.selectPalette.SelectPaletteDialog
import nyc.jsjrobotics.palmrgb.fragments.dialogs.saveFrame.SaveRgbFrameDialog
import nyc.jsjrobotics.palmrgb.fragments.dialogs.saveFrame.SaveRgbFrameDialogModel
import nyc.jsjrobotics.palmrgb.fragments.dialogs.selectPalette.SelectPaletteModel
import javax.inject.Inject

class CreateFramePresenter @Inject constructor(val saveRgbModel : SaveRgbFrameDialogModel,
                                               val changeDisplayModel : ChangeDisplayDialogModel,
                                               val selectPaletteModel: SelectPaletteModel,
                                               val createFrameModel: CreateFrameModel) : DefaultPresenter() {
    private lateinit var view: CreateFrameView
    private val disposables : CompositeDisposable = CompositeDisposable()
    private var displayedDialog : DialogFragmentWithPresenter? = null

    fun init(fragmentManager : FragmentManager, view: CreateFrameView) {
        this.view = view
        disposables.clear()
        val createDialogSubscription = view.onCreateFrameClicked.subscribe {
            displayDialog(fragmentManager, SaveRgbFrameDialog())
        }
        val saveFrameSubscription = saveRgbModel.onSaveFrameRequested.subscribe { frameTitle ->
            createFrameModel.writeCurrentFrameToDatabase(frameTitle)
        }
        val createPaletteClicked = selectPaletteModel.onCreatePaletteSelected.subscribe {
            activityNeeded.onNext { activity ->
                activity.showFragment(FragmentId.CREATE_PALETTE, addToBackStack = FragmentId.CREATE_PALETTE.tag)
            }
        }
        val selectPaletteClicked = view.onSelectPaletteClicked.subscribe {
            displayDialog(fragmentManager, SelectPaletteDialog())
        }
        val changeDisplayClicked = view.onChangeDisplayClicked.subscribe {
            displayDialog(fragmentManager, ChangeDisplayDialog())
        }
        disposables.addAll(
                createDialogSubscription,
                saveFrameSubscription,
                selectPaletteClicked,
                changeDisplayClicked,
                createPaletteClicked
        )
    }

    private fun displayDialog(fragmentManager: FragmentManager, dialog : DialogFragmentWithPresenter) {
        displayedDialog?.dismiss()
        dialog.show(fragmentManager, dialog.tag)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unsubscribe() {
        disposables.clear()
    }

}