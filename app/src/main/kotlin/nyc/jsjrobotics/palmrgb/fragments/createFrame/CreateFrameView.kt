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
import nyc.jsjrobotics.palmrgb.customViews.XmlDiodeArray64
import nyc.jsjrobotics.palmrgb.inflate
import java.util.*
import javax.inject.Inject

class CreateFrameView @Inject constructor(val createFrameModel: CreateFrameModel,
                                          val diodeArray: XmlDiodeArray64) : LifecycleObserver {
    lateinit var rootXml: View
    private lateinit var createFrameButton : Button
    private lateinit var resetFrameButton : Button
    private lateinit var selectPaletteButton : Button
    private lateinit var changeDisplayButton : Button

    private val selectPaletteClicked : PublishSubject<Boolean> = PublishSubject.create()
    private val createFrameClicked : PublishSubject<Boolean> = PublishSubject.create()
    private val changeDisplayClicked : PublishSubject<Boolean> = PublishSubject.create()

    val onCreateFrameClicked : Observable<Boolean> = createFrameClicked
    val onSelectPaletteClicked : Observable<Boolean> = selectPaletteClicked
    val onChangeDisplayClicked : Observable<Boolean> = changeDisplayClicked


    fun initView(container: ViewGroup, savedInstanceState:  Bundle?) {
        rootXml = container.inflate(R.layout.fragment_create_frame)
        diodeArray.setView(rootXml.findViewById(R.id.rgbMatrix))
        resetFrameButton = rootXml.findViewById(R.id.reset_frame)
        changeDisplayButton = rootXml.findViewById(R.id.change_display)
        selectPaletteButton = rootXml.findViewById(R.id.select_palette)
        createFrameButton = rootXml.findViewById(R.id.create_frame)

        createFrameButton.setOnClickListener { createFrameClicked.onNext(true) }
        selectPaletteButton.setOnClickListener { selectPaletteClicked.onNext(true) }
        resetFrameButton.setOnClickListener { diodeArray.reset() }
        changeDisplayButton.setOnClickListener{ changeDisplayClicked.onNext(true) }
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
                    val diode = diodeArray.getDiode(index) as RgbDiode?
                    if (diode != null) {
                        createFrameModel.saveDiodeState(index, diode.currentColor())
                    }
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unsubscribe() {
        diodeArray.unsubscribe()
    }

    fun notifyDataSetChanged() = diodeArray.notifyPaletteChanged()

}