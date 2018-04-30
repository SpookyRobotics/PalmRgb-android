package nyc.jsjrobotics.palmrgb.service

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class HardwareState @Inject constructor(){
    val urlChanged: PublishSubject<String> = PublishSubject.create()
    val onUrlChanged: Observable<String> = urlChanged
    var isConnected: Boolean = false
    var url: String = "" ; set(value) {
        field = value
        urlChanged.onNext(url)
    }

}
