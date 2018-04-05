package nyc.jsjrobotics.palmrgb.dialogs.changeDisplay

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Singleton

@Singleton
class ChangeDisplayDialogModel private constructor(){
    companion object {
        private val instance: ChangeDisplayDialogModel = ChangeDisplayDialogModel()
        fun instance() : ChangeDisplayDialogModel {
            return instance
        }
    }

    private val changeDisplay : PublishSubject<String> = PublishSubject.create()
    val onChangeDisplayRequested : Observable<String> = changeDisplay

    fun requestSaveCurrentFrame(title: String) {
        changeDisplay.onNext(title)
    }
}