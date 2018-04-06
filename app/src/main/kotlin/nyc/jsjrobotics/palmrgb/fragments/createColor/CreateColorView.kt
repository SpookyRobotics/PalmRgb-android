package nyc.jsjrobotics.palmrgb.fragments.createColor

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.inflate
import javax.inject.Inject

class CreateColorView @Inject constructor() {
    lateinit var rootXml: View

    private val greenValueChanged: PublishSubject<Int> = PublishSubject.create()
    private val redValueChanged: PublishSubject<Int> = PublishSubject.create()
    private val blueValueChanged: PublishSubject<Int> = PublishSubject.create()

    val onGreenValueChanged: Observable<Int> = greenValueChanged
    val onBlueValueChanged: Observable<Int> = blueValueChanged
    val onRedValueChanged: Observable<Int> = redValueChanged


    fun initView(container: ViewGroup, savedInstanceState: Bundle?) {
        rootXml = container.inflate(R.layout.fragment_create_color)

    }
}