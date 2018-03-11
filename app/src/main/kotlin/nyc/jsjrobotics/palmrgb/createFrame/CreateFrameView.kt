package nyc.jsjrobotics.palmrgb.createFrame

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.customViews.RgbDiode
import nyc.jsjrobotics.palmrgb.inflate
import javax.inject.Inject

class CreateFrameView @Inject constructor(){
    lateinit var rootXml: View
    lateinit var gridView: GridView
    var gridAdapter : RgbDiodeAdapter = RgbDiodeAdapter()
    lateinit var createFrameButton : Button
    private val createFrameClicked : PublishSubject<Boolean> = PublishSubject.create()
    val onCreateFrameClicked : Observable<Boolean> = createFrameClicked

    fun initView(container: ViewGroup) {
        if (true) {
            initView2(container)
            return
        }
        rootXml = container.inflate(R.layout.fragment_create_frame)
        createFrameButton = rootXml.findViewById(R.id.create_frame)
        createFrameButton.setOnClickListener { createFrameClicked.onNext(true) }
    }

    private fun initView2(container: ViewGroup) {
        rootXml = container.inflate(R.layout.fragment_create_frame2)
        gridView = rootXml.findViewById(R.id.rgbMatrix)
        gridView.adapter = gridAdapter
        createFrameButton = rootXml.findViewById(R.id.create_frame)
        createFrameButton.setOnClickListener { createFrameClicked.onNext(true) }    }

    fun getDiode(diodeIndex: Int): RgbDiode {
        val rowIndex = diodeIndex / 8
        val column = diodeIndex % 8
        val row = getRow(rowIndex)
        return getDiodeInRow(row, column)
    }

    fun getDiode(rowIndex : Int, column: Int): RgbDiode {
        val row = getRow(rowIndex)
        return getDiodeInRow(row, column)
    }

    /***
     * Get 0 based row viewgroup that holds diodes
     */
    fun getRow(rowIndex: Int) : ViewGroup {
        val groupId : Int
        when (rowIndex) {
            0 -> groupId = R.id.row1
            1 -> groupId = R.id.row2
            2 -> groupId = R.id.row3
            3 -> groupId = R.id.row4
            4 -> groupId = R.id.row5
            5 -> groupId = R.id.row6
            6 -> groupId = R.id.row7
            7 -> groupId = R.id.row8
            else -> throw IllegalArgumentException("Invalid index")
        }
        return rootXml.findViewById<ViewGroup>(groupId)
    }

    /***
     * Get 0 based diode from a viewgroup
     */
    fun getDiodeInRow(row : ViewGroup, index : Int) : RgbDiode {
        return row.getChildAt(index) as RgbDiode
    }

    fun currentFrame(): List<Int> {
        return IntRange(0, 63).map { getDiode(it).currentColor() }
    }
}