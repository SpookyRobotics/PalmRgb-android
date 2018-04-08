package nyc.jsjrobotics.palmrgb.fragments.createPalette

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v4.app.FragmentManager
import io.reactivex.disposables.CompositeDisposable
import nyc.jsjrobotics.palmrgb.androidInterfaces.DefaultPresenter
import nyc.jsjrobotics.palmrgb.dataStructures.SavedColorsModel
import nyc.jsjrobotics.palmrgb.fragments.dialogs.DialogFragmentWithPresenter
import nyc.jsjrobotics.palmrgb.fragments.dialogs.saveColor.SaveColorDialog
import nyc.jsjrobotics.palmrgb.fragments.dialogs.saveColor.SaveColorDialogModel
import javax.inject.Inject

class CreatePalettePresenter @Inject constructor(val saveColorDialogModel: SaveColorDialogModel,
                                                 val savedColors : SavedColorsModel) : DefaultPresenter(){
    private lateinit var view: CreatePaletteView
    private val disposables : CompositeDisposable = CompositeDisposable()
    private var displayedDialog : DialogFragmentWithPresenter? = null

    fun init(fragmentManager : FragmentManager, view: CreatePaletteView) {
        this.view = view
        subscribeToCreateColor(fragmentManager)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unsubscribe() {
        disposables.clear()
    }

    private fun subscribeToCreateColor(fragmentManager: FragmentManager) {
        val createColorSelected = view.createColorSelected().subscribe {
            activityNeeded.onNext {
                val createdColor = view.getCreateColorInput()
                saveColorDialogModel.colorToSave = createdColor
                displayedDialog?.dismiss()
                val dialog = SaveColorDialog()
                dialog.show(fragmentManager, dialog.tag)
            }
        }
        val colorNameSelected = saveColorDialogModel.onSaveColorRequested.subscribe{ colorName ->
            saveColorDialogModel.colorToSave?.let { colorToSave ->
                savedColors.saveNewColor(colorName, colorToSave)
            }
        }
        disposables.addAll(createColorSelected, colorNameSelected)
    }

}
