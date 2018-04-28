package nyc.jsjrobotics.palmrgb.fragments.createFrame

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.customViews.RgbDiode
import nyc.jsjrobotics.palmrgb.customViews.XmlDiodeArray
import nyc.jsjrobotics.palmrgb.inflate
import javax.inject.Inject

class CreateFrameView @Inject constructor(val createFrameModel: CreateFrameModel,
                                          val largeDiodeArray: XmlDiodeArray,
                                          val smallDiodeArray: XmlDiodeArray) : LifecycleObserver {
    lateinit var rootXml: View
    private lateinit var createFrameButton : Button
    private lateinit var resetFrameButton : Button
    private lateinit var selectPaletteButton : Button
    private lateinit var changeDisplayButton : Button
    private lateinit var largeMatrix : ConstraintLayout
    private lateinit var smallMatrix : ConstraintLayout

    private val selectPaletteClicked : PublishSubject<Boolean> = PublishSubject.create()
    private val createFrameClicked : PublishSubject<Boolean> = PublishSubject.create()
    private val changeDisplayClicked : PublishSubject<Boolean> = PublishSubject.create()

    val onCreateFrameClicked : Observable<Boolean> = createFrameClicked
    val onSelectPaletteClicked : Observable<Boolean> = selectPaletteClicked
    val onChangeDisplayClicked : Observable<Boolean> = changeDisplayClicked


    fun initView(container: ViewGroup, savedInstanceState:  Bundle?) {
        rootXml = container.inflate(R.layout.fragment_create_frame)
        largeMatrix = rootXml.findViewById(R.id.rgbMatrix64)
        smallMatrix = rootXml.findViewById(R.id.rgbMatrix32)
        displayLargeArray(createFrameModel.usingLargeArray)
        resetFrameButton = rootXml.findViewById(R.id.reset_frame)
        changeDisplayButton = rootXml.findViewById(R.id.change_display)
        selectPaletteButton = rootXml.findViewById(R.id.select_palette)
        createFrameButton = rootXml.findViewById(R.id.create_frame)
        largeDiodeArray.setView(largeMatrix)
        largeDiodeArray.subscribeOnDiodeChanged { diode -> saveDiodeColor(diode) }
        smallDiodeArray.setView(smallMatrix)
        smallDiodeArray.subscribeOnDiodeChanged { diode -> saveDiodeColor(diode) }
        refreshColors()
        createFrameButton.setOnClickListener { createFrameClicked.onNext(true) }
        selectPaletteButton.setOnClickListener { selectPaletteClicked.onNext(true) }
        resetFrameButton.setOnClickListener { handleReset() }
        changeDisplayButton.setOnClickListener{ changeDisplayClicked.onNext(true) }
    }

    private fun handleReset() {
        createFrameModel.reset()
        updateMatrixPaletteColors()
        largeDiodeArray.showColors(createFrameModel.displayedColors)
        smallDiodeArray.showColors(createFrameModel.displayedColors)

    }

    private fun diodeArray(): XmlDiodeArray {
        if (createFrameModel.usingLargeArray) {
            return largeDiodeArray
        }
        return smallDiodeArray
    }

    private fun refreshColors() {
        updateMatrixPaletteColors()
        diodeArray().showColors(createFrameModel.displayedColors)
    }

    fun updateMatrixPaletteColors() {
        largeDiodeArray.setPaletteColors(createFrameModel.selectedPalette.colors)
        smallDiodeArray.setPaletteColors(createFrameModel.selectedPalette.colors)
    }

    fun setSelectedPaletteName(name: String) {
        selectPaletteButton.text = rootXml.context.getString(R.string.selected_palette, name)
    }

    /***
     * When saving state, all diodes may not currently be on screen.
     * Hence we can only save state of displayed diodes.
     * When detached, other diodes must also save theirstate
     */
    fun writeStateToModel() {
        createFrameModel.diodeRange()
                .forEach { index ->
                    val diode = diodeArray().getDiode(index) as RgbDiode?
                    if (diode != null) {
                        createFrameModel.saveDiodeState(index, diode.currentColor())
                    }
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unsubscribe() {
        largeDiodeArray.unsubscribe()
        smallDiodeArray.unsubscribe()
    }

    private fun saveDiodeColor(view: RgbDiode) {
        createFrameModel.saveDiodeState(view.indexInMatrix, view.currentColor())
    }

    fun displayLargeArray(useLargeArray: Boolean) {
        if (useLargeArray) {
            largeMatrix.visibility = View.VISIBLE
            smallMatrix.visibility = View.GONE
        } else {
            largeMatrix.visibility = View.GONE
            smallMatrix.visibility = View.VISIBLE
        }
    }

}