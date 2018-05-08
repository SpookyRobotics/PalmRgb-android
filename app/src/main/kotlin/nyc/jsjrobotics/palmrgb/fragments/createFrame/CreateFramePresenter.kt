package nyc.jsjrobotics.palmrgb.fragments.createFrame

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v4.app.FragmentManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import nyc.jsjrobotics.palmrgb.androidInterfaces.DefaultPresenter
import nyc.jsjrobotics.palmrgb.fragments.dialogs.DialogFragmentWithPresenter
import nyc.jsjrobotics.palmrgb.fragments.dialogs.changeDisplay.ChangeDisplayDialog
import nyc.jsjrobotics.palmrgb.fragments.dialogs.changeDisplay.ChangeDisplayDialogModel
import nyc.jsjrobotics.palmrgb.fragments.dialogs.saveFrame.SaveRgbFrameDialog
import nyc.jsjrobotics.palmrgb.fragments.dialogs.saveFrame.SaveRgbFrameDialogModel
import nyc.jsjrobotics.palmrgb.fragments.dialogs.selectPalette.SelectPaletteDialog
import nyc.jsjrobotics.palmrgb.fragments.dialogs.selectPalette.SelectPaletteModel
import nyc.jsjrobotics.palmrgb.globalState.SavedPaletteModel
import nyc.jsjrobotics.palmrgb.runOnMainThread
import javax.inject.Inject

class CreateFramePresenter @Inject constructor(val saveRgbModel : SaveRgbFrameDialogModel,
                                               val changeDisplayModel : ChangeDisplayDialogModel,
                                               val createFrameModel: CreateFrameModel,
                                               val savedPaletteModel: SavedPaletteModel,
                                               private val selectPaletteModel: SelectPaletteModel) : DefaultPresenter() {
    private lateinit var view: CreateFrameView
    private val disposables : CompositeDisposable = CompositeDisposable()
    private var displayedDialog : DialogFragmentWithPresenter? = null
    private var loadingPaletteDisposable : Disposable? = null

    fun init(fragmentManager : FragmentManager, view: CreateFrameView) {
        this.view = view
        disposables.clear()
        subscribeToCreateDialog(fragmentManager)
        subscribeToSaveFrame()
        subscribeToSelectChangePalette(fragmentManager)
        subscribeToChangeDisplay(fragmentManager)
        subscribeToPaletteChanged()
        subscribeToReset()
        setCurrentPaletteName()
    }

    private fun subscribeToReset() {
        val resetClicked = view.onResetClicked.subscribe {
            createFrameModel.beginReset()
            view.handleReset()
            createFrameModel.endReset()
        }
        disposables.add(resetClicked)
    }

    private fun setCurrentPaletteName() {
        view.setSelectedPaletteName(createFrameModel.selectedPalette.name)
    }

    private fun subscribeToPaletteChanged() {
        selectPaletteModel.onPaletteSelected.subscribe{ ignored ->
            runOnMainThread {
                setCurrentPaletteName()
                view.updateMatrixPaletteColors()
            }

        }
    }

    private fun subscribeToChangeDisplay(fragmentManager: FragmentManager) {
        val displaySelected = changeDisplayModel.onChangeDisplayRequested.subscribe { useLargeArray ->
            createFrameModel.usingLargeArray = useLargeArray
            view.displayLargeArray(useLargeArray)
            createFrameModel.setDisplayingColors(view.getDisplayingColors())
        }
        val changeDisplayClicked = view.onChangeDisplayClicked.subscribe {
            displayDialog(fragmentManager, ChangeDisplayDialog())
        }
        disposables.addAll(changeDisplayClicked, displaySelected)
    }

    private fun subscribeToSelectChangePalette(fragmentManager: FragmentManager) {
        val selectPaletteClicked = view.onSelectPaletteClicked.subscribe {
            loadingPaletteDisposable?.dispose()
            loadingPaletteDisposable = savedPaletteModel.loadPaletteList().subscribe { ignored ->
                // After refreshing the palette list, showColors the dialog
                displayDialog(fragmentManager, SelectPaletteDialog())
            }
        }
        disposables.add(selectPaletteClicked)
    }

    private fun subscribeToSaveFrame() {
        val saveFrameSubscription = saveRgbModel.onSaveFrameRequested.subscribe { frameTitle ->
            createFrameModel.writeCurrentFrameToDatabase(frameTitle)
        }
        disposables.add(saveFrameSubscription)
    }

    private fun subscribeToCreateDialog(fragmentManager: FragmentManager) {
        val createDialogSubscription = view.onCreateFrameClicked.subscribe {
            displayDialog(fragmentManager, SaveRgbFrameDialog())
        }
        disposables.add(createDialogSubscription)
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