package nyc.jsjrobotics.palmrgb.fragments.dialogs.changeDisplay

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

    private val changeDisplay : PublishSubject<Boolean> = PublishSubject.create()
    val onChangeDisplayRequested : Observable<Boolean> = changeDisplay

    fun requestChangeDisplay(largeDisplay: Boolean) {
        changeDisplay.onNext(largeDisplay)
    }
}