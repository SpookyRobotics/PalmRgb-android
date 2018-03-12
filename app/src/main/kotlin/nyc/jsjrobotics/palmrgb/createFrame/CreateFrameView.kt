package nyc.jsjrobotics.palmrgb.createFrame

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
import java.util.ArrayList
import javax.inject.Inject

class CreateFrameView @Inject constructor(val gridAdapter: RgbDiodeAdapter,
                                          val createFrameModel: CreateFrameModel){
    lateinit var rootXml: View
    private var savedState: ArrayList<Int>? = null
    lateinit var gridView: GridView
    lateinit var createFrameButton : Button
    private val createFrameClicked : PublishSubject<Boolean> = PublishSubject.create()
    val onCreateFrameClicked : Observable<Boolean> = createFrameClicked

    fun initView(container: ViewGroup, savedInstanceState: Bundle?) {
        if (true) {
            initView2(container, savedInstanceState)
            return
        }
        rootXml = container.inflate(R.layout.fragment_create_frame)
        createFrameButton = rootXml.findViewById(R.id.create_frame)
        createFrameButton.setOnClickListener { createFrameClicked.onNext(true) }
    }

    private fun initView2(container: ViewGroup, savedInstanceState:  Bundle?) {
        rootXml = container.inflate(R.layout.fragment_create_frame)
        gridView = rootXml.findViewById(R.id.rgbMatrix)
        gridView.adapter = gridAdapter
        savedInstanceState?.let { onRestoreInstanceState(it) }
        createFrameButton = rootXml.findViewById(R.id.create_frame)
        createFrameButton.setOnClickListener { createFrameClicked.onNext(true) }    }

    fun getDiode(diodeIndex: Int): RgbDiode {
        return gridView.getChildAt(diodeIndex) as RgbDiode
    }

    /***
     * Get 0 based diode from a viewgroup
     */
    fun getDiodeInRow(row : ViewGroup, index : Int) : RgbDiode {
        return row.getChildAt(index) as RgbDiode
    }

    fun currentFrame(): List<Int> {
        return createFrameModel.diodeRange()
                .map { getDiode(it).currentColor() }
    }

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
}