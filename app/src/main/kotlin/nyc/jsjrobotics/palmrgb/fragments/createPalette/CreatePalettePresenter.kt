package nyc.jsjrobotics.palmrgb.fragments.createPalette

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v4.app.FragmentManager
import android.widget.Toast
import io.reactivex.disposables.CompositeDisposable
import nyc.jsjrobotics.palmrgb.androidInterfaces.DefaultPresenter
import nyc.jsjrobotics.palmrgb.dataStructures.SavedColorsModel
import nyc.jsjrobotics.palmrgb.dataStructures.SavedPaletteModel
import nyc.jsjrobotics.palmrgb.database.AppDatabase
import nyc.jsjrobotics.palmrgb.fragments.dialogs.DialogFragmentWithPresenter
import javax.inject.Inject

class CreatePalettePresenter @Inject constructor(val appDatabase: AppDatabase,
                                                 val savedColorsModel: SavedColorsModel,
                                                 val createPaletteModel: CreatePaletteModel) : DefaultPresenter(){
    private lateinit var view: CreatePaletteView
    private val disposables : CompositeDisposable = CompositeDisposable()
    private var displayedDialog : DialogFragmentWithPresenter? = null

    fun init(fragmentManager : FragmentManager, view: CreatePaletteView) {
        this.view = view
        subscribeToInsertColor()
        subscribeToAddColorToPalette()
        loadStandardColors()
        loadSavedColors()
        updateCreatePaletteButton()
        subscribeCreatePaletteButton()
    }

    private fun subscribeCreatePaletteButton() {
        val createPaletteDisposable = view.onCreatePaletteSelected.subscribe {
            // Save created palette by showing dialog
        }
        disposables.add(createPaletteDisposable)
    }

    private fun loadStandardColors() {
        view.standardColors = savedColorsModel.standardColors()
    }

    private fun loadSavedColors() {
        val loadColors = savedColorsModel.loadSavedColors().subscribe { options ->
            view.savedColors = options
        }
        disposables.add(loadColors)
    }

    private fun subscribeToAddColorToPalette() {
        val addColorDisposable = view.onAddColor().subscribe { colorSelected ->
            createPaletteModel.addColorOption(colorSelected)
            view.addPaletteColor(colorSelected)
            updateCreatePaletteButton()
        }
        disposables.add(addColorDisposable)
    }

    private fun updateCreatePaletteButton() {
        view.showCreatePaletteButton(!view.getCreatePaletteColors().isEmpty())
    }

    private fun subscribeToInsertColor() {
        val saveColorsModified = appDatabase.savedColorsDao().getAllFlowable().subscribe {
            loadSavedColors()
        }
        disposables.add(saveColorsModified)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unsubscribe() {
        disposables.clear()
    }
}
