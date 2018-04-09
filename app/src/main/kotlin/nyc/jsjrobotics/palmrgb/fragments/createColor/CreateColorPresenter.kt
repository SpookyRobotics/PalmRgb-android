package nyc.jsjrobotics.palmrgb.fragments.createColor

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v4.app.FragmentManager
import io.reactivex.disposables.CompositeDisposable
import nyc.jsjrobotics.palmrgb.androidInterfaces.DefaultPresenter
import nyc.jsjrobotics.palmrgb.dataStructures.SavedColorsModel
import nyc.jsjrobotics.palmrgb.database.AppDatabase
import nyc.jsjrobotics.palmrgb.fragments.createPalette.CreatePaletteView
import nyc.jsjrobotics.palmrgb.fragments.dialogs.DialogFragmentWithPresenter
import nyc.jsjrobotics.palmrgb.fragments.dialogs.saveColor.SaveColorDialog
import nyc.jsjrobotics.palmrgb.fragments.dialogs.saveColor.SaveColorDialogModel
import javax.inject.Inject

class CreateColorPresenter @Inject constructor(val appDatabase: AppDatabase,
                                               val saveColorDialogModel: SaveColorDialogModel,
                                               val savedColors : SavedColorsModel) : DefaultPresenter(){

    private lateinit var view: CreateColorView
    private val disposables : CompositeDisposable = CompositeDisposable()
    private var displayedDialog : DialogFragmentWithPresenter? = null

    fun init(fragmentManager : FragmentManager, view: CreateColorView) {
        this.view = view
        subscribeToCreateColor(fragmentManager)
        subscribeToInsertColor()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unsubscribe() {
        disposables.clear()
    }

    private fun subscribeToInsertColor() {
        val saveColorsModified = appDatabase.savedColorsDao().getAllFlowable().subscribe {
            //view.refreshSavedColors()
        }
        disposables.add(saveColorsModified)
    }

    private fun subscribeToCreateColor(fragmentManager: FragmentManager) {
        val createColorSelected = view.onCreateColorSelected.subscribe {
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