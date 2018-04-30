package nyc.jsjrobotics.palmrgb.service

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HardwareState @Inject constructor(){
    val urlChanged: PublishSubject<String> = PublishSubject.create()
    val onUrlChanged: Observable<String> = urlChanged
    var isConnected: Boolean = false
    private var url: String = "" ; private set
    private var port : String = "" ; private set

    fun setUrl(newUrl: String, newPort: String) {
        url = newUrl
        port = newPort
        urlChanged.onNext(getUrl())
    }

    fun getUrl(): String {
        if (url.isBlank() || port.isBlank()) {
            return ""
        }
        return "http://$url:$port/"
    }

}
