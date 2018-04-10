package nyc.jsjrobotics.palmrgb.fragments.dialogs.savePalette

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.fragments.dialogs.saveColor.SaveColorDialogModel
import javax.inject.Singleton

@Singleton
class SavePaletteDialogModel private constructor(){
    companion object {
        private val instance: SavePaletteDialogModel = SavePaletteDialogModel()
        fun instance() : SavePaletteDialogModel {
            return instance
        }
    }

    private val saveColor : PublishSubject<String> = PublishSubject.create()
    val onSaveColorRequested : Observable<String> = saveColor

    var colorToSave : Int? = null

    fun requestSaveColorOption(title: String) {
        saveColor.onNext(title)
    }
}