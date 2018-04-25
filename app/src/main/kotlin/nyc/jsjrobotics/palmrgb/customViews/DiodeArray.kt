package nyc.jsjrobotics.palmrgb.customViews

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import nyc.jsjrobotics.palmrgb.R


class DiodeArray(context: Context, attrs: AttributeSet?, style: Int) : ConstraintLayout(context, attrs, style) {

    private val ROW_COUNT: Int = 8
    private val SQUARE_COLUMN_COUNT: Int = 8
    private val RECTANGLE_COLUMN_COUNT: Int = 4
    private val diodeIdMap: MutableMap<Int, Int> = mutableMapOf()
    private val diodes: MutableList<RgbDiode> = mutableListOf()


    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    private var columnCount: Int = SQUARE_COLUMN_COUNT; set(value) {
        if (value >= SQUARE_COLUMN_COUNT) {
            field = SQUARE_COLUMN_COUNT
        } else {
            field = RECTANGLE_COLUMN_COUNT
        }
    }

    init {
        val constraintSet = ConstraintSet()
        diodes.addAll(buildDiodes())
        updateDiodeMap(diodes)
        val rowMap = buildDiodeRowMap(diodes)
        val columnMap = buildDiodeColumnMap(diodes)

        diodes.forEachIndexed { index, diode ->
            diode.indexInMatrix = index
            addView(diode)
            constraintSet.constrainWidth(diode.id, 100)
            constraintSet.constrainHeight(diode.id, 100)
        }
        rowMap.forEach { rowIndex, diodesInRow ->
            buildHorizontalChain(constraintSet, diodesInRow)
        }

        columnMap.forEach { columnIndex, diodesInColumn ->
            buildVerticalChain(constraintSet, diodesInColumn)
        }
        constraintSet.applyTo(this)
    }

    private fun buildHorizontalChain(constraintSet: ConstraintSet, diodesInRow: List<RgbDiode>) {
        val horizontalChain = diodesInRow.map { it.id }.toIntArray()
        constraintSet.createHorizontalChain(
                ConstraintSet.PARENT_ID,
                ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.LEFT,
                horizontalChain,
                null,
                ConstraintSet.CHAIN_SPREAD_INSIDE
        )
    }

    private fun buildVerticalChain(constraintSet: ConstraintSet, diodesInColumn: List<RgbDiode>) {
        val verticalChain = diodesInColumn.map { it.id }.toIntArray()
        constraintSet.createVerticalChain(
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP,
                verticalChain,
                null,
                ConstraintSet.CHAIN_SPREAD_INSIDE
        )
    }

    private fun buildDiodeRowMap(diodes: List<RgbDiode>): Map<Int, List<RgbDiode>> {
        return diodes.groupBy { diodes.indexOf(it) / columnCount }
    }

    private fun buildDiodeColumnMap(diodes: List<RgbDiode>): Map<Int, List<RgbDiode>> {
        return diodes.groupBy { diodes.indexOf(it) % columnCount }
    }

    private fun updateDiodeMap(diodes: List<RgbDiode>) {
        diodes.forEachIndexed { index, rgbDiode ->
            diodeIdMap[index] = rgbDiode.id
        }
    }

    private fun buildDiodes(): List<RgbDiode> {
        val result : MutableList<RgbDiode> = mutableListOf()
        IntRange(0, ROW_COUNT-1).forEach {
            IntRange(0, columnCount-1).forEach {
                val inflater = LayoutInflater.from(context)
                val diode = inflater.inflate(R.layout.single_diode, null, false) as RgbDiode
                diode.id = View.generateViewId()
                result.add(diode)
            }
        }
        return result
    }
}
