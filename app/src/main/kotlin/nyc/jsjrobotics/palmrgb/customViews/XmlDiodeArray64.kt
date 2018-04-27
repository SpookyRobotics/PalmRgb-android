package nyc.jsjrobotics.palmrgb.customViews

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import io.reactivex.disposables.CompositeDisposable
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.fragments.createFrame.CreateFrameModel
import javax.inject.Inject

class XmlDiodeArray64 @Inject constructor(val createFrameModel : CreateFrameModel) {

    private lateinit var rootXml: ConstraintLayout
    private val disposables: CompositeDisposable = CompositeDisposable()

    fun setView(layout : ConstraintLayout) {
        rootXml = layout
        setPaletteColors(createFrameModel.selectedPalette.colors.toMutableList())
    }

    fun getDiode(index: Int): RgbDiode {
        return rootXml.getChildAt(index) as RgbDiode
    }


    fun setPaletteColors(palette: List<Int>) {
        getIndexedDiodes()
                .forEach {
                    val index = it.first
                    val diode = it.second
                    diode.colorStateList = palette.toMutableList()
                    diode.indexInMatrix = index
                    val colorToDisplay = createFrameModel.displayedColors.get(index)
                    diode.setCurrentColor(colorToDisplay)
                    diode.subscribeOnColorChanged { saveViewColor(diode) }
                }
    }

    fun reset() {
        createFrameModel.reset()
        notifyPaletteChanged()
    }

    private fun getIndexedDiodes(): List<Pair<Int, RgbDiode>> {
        return getDiodeRange()
                .map { index -> Pair(index, getDiode(index)) }
    }

    private fun getDiodeRange(): IntRange {
        return IntRange(0, rootXml.childCount-1)
    }

    private fun saveViewColor(view: RgbDiode) {
        createFrameModel.saveDiodeState(view.indexInMatrix, view.currentColor())
    }

    fun unsubscribe() {
        getIndexedDiodes().forEach { it.second.clearSubscriptions() }
    }

    fun notifyPaletteChanged() {
        getIndexedDiodes().forEach {
            val index = it.first
            val diode = it.second
            val colorToDisplay = createFrameModel.displayedColors.get(index)
            diode.setCurrentColor(colorToDisplay)
            diode.invalidate()
        }
    }

    companion object {
        private fun inflateView(context: Context): ConstraintLayout {
            return LayoutInflater.from(context).inflate(R.layout.diode_array_64, null, false) as ConstraintLayout
        }
    }
}