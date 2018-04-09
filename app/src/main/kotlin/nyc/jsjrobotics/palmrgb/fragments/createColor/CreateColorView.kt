package nyc.jsjrobotics.palmrgb.fragments.createColor

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.customViews.RgbDiode
import nyc.jsjrobotics.palmrgb.inflate
import javax.inject.Inject

class CreateColorView @Inject constructor() {

    lateinit var rootXml: View
    private val greenValueChanged: PublishSubject<Int> = PublishSubject.create()
    private val redValueChanged: PublishSubject<Int> = PublishSubject.create()
    private val blueValueChanged: PublishSubject<Int> = PublishSubject.create()
    private val createColorSelected : PublishSubject<Boolean> = PublishSubject.create()

    val onGreenValueChanged: Observable<Int> = greenValueChanged
    val onBlueValueChanged: Observable<Int> = blueValueChanged
    val onRedValueChanged: Observable<Int> = redValueChanged
    val onCreateColorSelected : Observable<Boolean> = createColorSelected

    private lateinit var greenSeekBar : SeekBar
    private lateinit var blueSeekBar : SeekBar
    private lateinit var redSeekBar : SeekBar
    private lateinit var rgbDiode : RgbDiode
    private lateinit var createColor : Button

    private val RGB_MAX: Int = 255

    fun initView(viewGroup: ViewGroup, savedInstanceState : Bundle?) {
        rootXml = viewGroup.inflate(R.layout.fragment_create_color)
        greenSeekBar = rootXml.findViewById(R.id.green_seekbar)
        blueSeekBar = rootXml.findViewById(R.id.blue_seekbar)
        redSeekBar = rootXml.findViewById(R.id.red_seekbar)
        rgbDiode = rootXml.findViewById(R.id.preview_diode)
        createColor = rootXml.findViewById(R.id.save)
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
