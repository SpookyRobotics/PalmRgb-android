package nyc.jsjrobotics.palmrgb

import android.view.View
import android.view.ViewGroup
import javax.inject.Inject

class CreateFrameView @Inject constructor(){
    lateinit var rootXml: View
    fun initView(container: ViewGroup) {
        rootXml = container.inflate(R.layout.fragment_create_frame)
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
}