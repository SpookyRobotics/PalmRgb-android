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

class CreateFrameView @Inject constructor(){
    lateinit var rootXml: View
    private var savedState: ArrayList<Int>? = null
    lateinit var gridView: GridView
    var gridAdapter : RgbDiodeAdapter = RgbDiodeAdapter()
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
        rootXml = container.inflate(R.layout.fragment_create_frame2)
        gridView = rootXml.findViewById(R.id.rgbMatrix)
        gridView.adapter = gridAdapter
        savedInstanceState?.let { onRestoreInstanceState(it) }
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
        return diodeRange().map { getDiode(it).currentColor() }
    }

    private fun diodeRange(): IntRange = IntRange(0, 64)

    /***
     * When saving state, all diodes may not currently be on screen.
     * Hence we can not only save state of displayed diodes, but also state of ones not attached to viewgroup
     * We use the savedState ArrayList to supply values for didoes that are off screen, and if no value is found
     * set it to 0
     */
    fun onSaveInstanceState(outState: Bundle) {
        val colorIndexes = diodeRange().map {
            val diode = gridView.getChildAt(it) as RgbDiode?
            if (diode != null) {
                return@map diode.currentColorIndex
            } else {
                savedState?.let { savedState -> return@map savedState[it] }
                return@map 0
            }
        }
        val saveState = arrayListOf<Int>()
        saveState.addAll(colorIndexes)
        outState.putIntegerArrayList("colors", saveState)
    }


    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        savedState = savedInstanceState.getIntegerArrayList("colors")
        gridAdapter.setRestoredState(savedState)
    }
}