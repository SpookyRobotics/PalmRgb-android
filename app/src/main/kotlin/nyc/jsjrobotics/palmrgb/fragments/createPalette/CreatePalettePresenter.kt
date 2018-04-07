package nyc.jsjrobotics.palmrgb.fragments.createPalette

import android.support.v4.app.FragmentManager
import io.reactivex.disposables.CompositeDisposable
import nyc.jsjrobotics.palmrgb.androidInterfaces.DefaultPresenter
import nyc.jsjrobotics.palmrgb.fragments.dialogs.DialogFragmentWithPresenter
import nyc.jsjrobotics.palmrgb.fragments.dialogs.saveColor.SaveColorDialog
import javax.inject.Inject

class CreatePalettePresenter @Inject constructor() : DefaultPresenter(){
    private lateinit var view: CreatePaletteView
    private val disposables : CompositeDisposable = CompositeDisposable()
    private var displayedDialog : DialogFragmentWithPresenter? = null

    fun init(fragmentManager : FragmentManager, view: CreatePaletteView) {
        this.view = view
        subscribeToCreateColor(fragmentManager)
    }

    private fun subscribeToCreateColor(fragmentManager: FragmentManager) {
        view.createColorSelected().subscribe {
            activityNeeded.onNext {
                displayedDialog?.dismiss()
                val dialog = SaveColorDialog()
                dialog.show(fragmentManager, dialog.tag)
            }
        }
    }

}
