package nyc.jsjrobotics.palmrgb.dialogs.saveFrame

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Singleton

@Singleton
class SaveRgbFrameDialogModel private constructor(){
    companion object {
        private val instance: SaveRgbFrameDialogModel = SaveRgbFrameDialogModel()
        fun instance() : SaveRgbFrameDialogModel {
            return instance
        }
    }

    private val saveCurrentFrame : PublishSubject<String> = PublishSubject.create()
    val onSaveFrameRequested : Observable<String> = saveCurrentFrame

    fun requestSaveCurrentFrame(title: String) {
        saveCurrentFrame.onNext(title)
    }
}