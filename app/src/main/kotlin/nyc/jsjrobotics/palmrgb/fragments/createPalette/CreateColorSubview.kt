package nyc.jsjrobotics.palmrgb.fragments.createPalette

import android.view.View
import android.widget.Button
import android.widget.SeekBar
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.customViews.RgbDiode

class CreateColorSubview(val rootXml: View) {
    private val greenValueChanged: PublishSubject<Int> = PublishSubject.create()
    private val redValueChanged: PublishSubject<Int> = PublishSubject.create()
    private val blueValueChanged: PublishSubject<Int> = PublishSubject.create()
    private val createColorSelected : PublishSubject<Boolean> = PublishSubject.create()

    val onGreenValueChanged: Observable<Int> = greenValueChanged
    val onBlueValueChanged: Observable<Int> = blueValueChanged
    val onRedValueChanged: Observable<Int> = redValueChanged
    val onCreateColorSelected : Observable<Boolean> = createColorSelected

    private val greenSeekBar : SeekBar = rootXml.findViewById(R.id.green_seekbar)
    private val blueSeekBar : SeekBar = rootXml.findViewById(R.id.blue_seekbar)
    private val redSeekBar : SeekBar = rootXml.findViewById(R.id.red_seekbar)
    private val rgbDiode : RgbDiode = rootXml.findViewById(R.id.preview_diode)
    private val createColor : Button = rootXml.findViewById(R.id.save)

    private val RGB_MAX: Int = 255

    init {
        greenSeekBar.max = RGB_MAX
        redSeekBar.max = RGB_MAX
        blueSeekBar.max = RGB_MAX
        greenSeekBar.setOnSeekBarChangeListener(handleGreenSeekBarChange())
        blueSeekBar.setOnSeekBarChangeListener(handleBlueSeekBarChange())
        redSeekBar.setOnSeekBarChangeListener(handleRedSeekBarChange())
        createColor.setOnClickListener { createColorSelected.onNext(true) }
    }

    private fun handleGreenSeekBarChange(): SeekBar.OnSeekBarChangeListener {
        return object: SeekBar.OnSeekBarChangeListener{
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                rgbDiode.setGreenComponent(progress)
                greenValueChanged.onNext(progress)
            }
        }
    }

    private fun handleBlueSeekBarChange(): SeekBar.OnSeekBarChangeListener {
        return object: SeekBar.OnSeekBarChangeListener{
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                rgbDiode.setBlueComponent(progress)
                blueValueChanged.onNext(progress)
            }
        }
    }

    private fun handleRedSeekBarChange(): SeekBar.OnSeekBarChangeListener {
        return object: SeekBar.OnSeekBarChangeListener{
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                rgbDiode.setRedComponent(progress)
                redValueChanged.onNext(progress)
            }
        }
    }

    fun getCreateColorInput(): Int = rgbDiode.currentColor()

}
