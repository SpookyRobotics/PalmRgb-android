package nyc.jsjrobotics.palmrgb.fragments.createPalette

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v4.app.FragmentManager
import android.widget.Toast
import io.reactivex.disposables.CompositeDisposable
import nyc.jsjrobotics.palmrgb.androidInterfaces.DefaultPresenter
import nyc.jsjrobotics.palmrgb.dataStructures.SavedColorsModel
import nyc.jsjrobotics.palmrgb.database.AppDatabase
import nyc.jsjrobotics.palmrgb.fragments.dialogs.DialogFragmentWithPresenter
import nyc.jsjrobotics.palmrgb.fragments.dialogs.saveColor.SaveColorDialog
import nyc.jsjrobotics.palmrgb.fragments.dialogs.saveColor.SaveColorDialogModel
import javax.inject.Inject

class CreatePalettePresenter @Inject constructor(val appDatabase: AppDatabase) : DefaultPresenter(){
    private lateinit var view: CreatePaletteView
    private val disposables : CompositeDisposable = CompositeDisposable()
    private var displayedDialog : DialogFragmentWithPresenter? = null

    fun init(fragmentManager : FragmentManager, view: CreatePaletteView) {
        this.view = view
        subscribeToInsertColor()
        subscribeToAddColorToPalette()
    }

    private fun subscribeToAddColorToPalette() {
        val addColorDisposable = view.onAddColor().subscribe {colorSelected ->
            activityNeeded.onNext {
                Toast.makeText(it.applicationContext(), "Selected ${colorSelected.title}", Toast.LENGTH_SHORT).show()
            }
        }
        disposables.add(addColorDisposable)
    }

    private fun subscribeToInsertColor() {
        val saveColorsModified = appDatabase.savedColorsDao().getAllFlowable().subscribe {
            view.refreshSavedColors()
        }
        disposables.add(saveColorsModified)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unsubscribe() {
        disposables.clear()
    }
}
