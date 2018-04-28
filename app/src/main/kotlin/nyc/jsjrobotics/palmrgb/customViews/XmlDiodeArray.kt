package nyc.jsjrobotics.palmrgb.customViews

import android.support.constraint.ConstraintLayout
import javax.inject.Inject

class XmlDiodeArray @Inject constructor() {

    private lateinit var rootXml: ConstraintLayout

    fun setView(layout : ConstraintLayout) {
        rootXml = layout
        getIndexedDiodes().forEach {
            val index = it.first
            val diode = it.second
            diode.indexInMatrix = index
        }
    }

    fun getDiode(index: Int): RgbDiode {
        return rootXml.getChildAt(index) as RgbDiode
    }


    fun setPaletteColors(palette: List<Int>) {
        getDiodes()
                .forEach { diode ->
                    diode.colorStateList = palette.toMutableList()
                }
    }

    fun subscribeOnDiodeChanged(onDiodeChanged: (diode: RgbDiode) -> Unit) {
        getDiodes().forEach {
            it.subscribeOnColorChanged { onDiodeChanged.invoke(it) }
        }
    }
    private fun getIndexedDiodes(): List<Pair<Int, RgbDiode>> {
        return getDiodeRange()
                .map { index -> Pair(index, getDiode(index)) }
    }

    private fun getDiodeRange(): IntRange {
        return IntRange(0, rootXml.childCount-1)
    }

    private fun getDiodes(): List<RgbDiode> {
        return getDiodeRange().map (this::getDiode)
    }

    fun unsubscribe() {
        getDiodes().forEach { it.clearSubscriptions() }
    }

    fun setDiodesClickable(isClickable: Boolean) {
        getDiodes().forEach { it.isClickable = isClickable }
    }

    fun setOnClickListener(onClick: () -> Unit) {
        rootXml.setOnClickListener { onClick.invoke() }
    }

    fun showColors(colorList: List<Int>) {
        getDiodes().zip(colorList).forEach { pair ->
            val diode = pair.first
            val colorToDisplay = pair.second
            diode.setCurrentColor(colorToDisplay)
        }
    }

    fun getColors() : List<Int> {
        return getDiodes().map { it.currentColor() }
    }
}