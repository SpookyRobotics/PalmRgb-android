package nyc.jsjrobotics.palmrgb.fragments.dialogs.saveColor

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Singleton

@Singleton
class SaveColorDialogModel private constructor(){
    companion object {
        private val instance: SaveColorDialogModel = SaveColorDialogModel()
        fun instance() : SaveColorDialogModel {
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