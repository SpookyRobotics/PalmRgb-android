package nyc.jsjrobotics.palmrgb.fragments.createFrame

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.customViews.RgbDiode
import nyc.jsjrobotics.palmrgb.inflate
import java.util.*
import javax.inject.Inject

class CreateFrameView @Inject constructor(val gridAdapter: RgbDiodeAdapter,
                                          val createFrameModel: CreateFrameModel){
    lateinit var rootXml: View
    private var savedState: ArrayList<Int>? = null
    private lateinit var gridView: GridView
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
        gridView = rootXml.findViewById(R.id.rgbMatrix)
        resetFrameButton = rootXml.findViewById(R.id.reset_frame)
        changeDisplayButton = rootXml.findViewById(R.id.change_display)
        selectPaletteButton = rootXml.findViewById(R.id.select_palette)
        createFrameButton = rootXml.findViewById(R.id.create_frame)

        gridView.adapter = gridAdapter
        savedInstanceState?.let { onRestoreInstanceState(it) }

        createFrameButton.setOnClickListener { createFrameClicked.onNext(true) }
        selectPaletteButton.setOnClickListener { selectPaletteClicked.onNext(true) }
        resetFrameButton.setOnClickListener { gridAdapter.reset() }
        changeDisplayButton.setOnClickListener{ changeDisplayClicked.onNext(true) }
    }

    fun getDiode(diodeIndex: Int): RgbDiode {
        return gridView.getChildAt(diodeIndex) as RgbDiode
    }

    /***
     * Get 0 based diode from a viewgroup
     */
    fun getDiodeInRow(row : ViewGroup, index : Int) : RgbDiode {
        return row.getChildAt(index) as RgbDiode
    }

    fun currentFrame(): List<Int> = createFrameModel.displayedColors

    /***
     * When saving state, all diodes may not currently be on screen.
     * Hence we can only save state of displayed diodes.
     * When detached, other diodes must also save theirstate
     */
    fun writeStateToModel() {
        createFrameModel.diodeRange()
                .forEach { index ->
                    val diode = gridView.getChildAt(index) as RgbDiode?
                    if (diode != null) {
                        createFrameModel.saveDiodeState(index, diode.currentColor())
                    }
        }
    }




    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        savedState = savedInstanceState.getIntegerArrayList("colors")
        //gridAdapter.setRestoredState(savedState)
    }

    fun notifyDataSetChanged() = gridAdapter.notifyDataSetChanged()
}