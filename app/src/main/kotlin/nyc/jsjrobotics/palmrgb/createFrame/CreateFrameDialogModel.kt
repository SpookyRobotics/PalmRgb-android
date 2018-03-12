package nyc.jsjrobotics.palmrgb.createFrame

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CreateFrameDialogModel {
    companion object {
        private val instance: CreateFrameDialogModel = CreateFrameDialogModel()
        fun instance() : CreateFrameDialogModel {
            return instance
        }
    }

    private val saveCurrentFrame : PublishSubject<String> = PublishSubject.create()
    val onSaveFrameRequested : Observable<String> = saveCurrentFrame

    fun requestSaveCurrentFrame(title: String) {
        saveCurrentFrame.onNext(title)
    }
}