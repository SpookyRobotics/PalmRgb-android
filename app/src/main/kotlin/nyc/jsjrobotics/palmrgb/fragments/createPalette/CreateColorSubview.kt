package nyc.jsjrobotics.palmrgb.fragments.createPalette

import android.view.View
import android.widget.SeekBar
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.R

class CreateColorSubview(val rootXml: View) {
    private val greenValueChanged: PublishSubject<Int> = PublishSubject.create()
    private val redValueChanged: PublishSubject<Int> = PublishSubject.create()
    private val blueValueChanged: PublishSubject<Int> = PublishSubject.create()

    val onGreenValueChanged: Observable<Int> = greenValueChanged
    val onBlueValueChanged: Observable<Int> = blueValueChanged
    val onRedValueChanged: Observable<Int> = redValueChanged

    private val greenSeekbar : SeekBar = rootXml.findViewById(R.id.green_seekbar)
    private val blueSeekbar : SeekBar = rootXml.findViewById(R.id.blue_seekbar)
    private val redSeekbar : SeekBar = rootXml.findViewById(R.id.red_seekbar)

}
