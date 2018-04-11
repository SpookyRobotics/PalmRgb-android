package nyc.jsjrobotics.palmrgb.fragments.createFrame

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.customViews.RgbDiode
import nyc.jsjrobotics.palmrgb.dataStructures.Palette
import nyc.jsjrobotics.palmrgb.fragments.dialogs.selectPalette.SelectPaletteModel
import nyc.jsjrobotics.palmrgb.inflate
import javax.inject.Inject

class RgbDiodeAdapter @Inject constructor(val createFrameModel : CreateFrameModel,
                                          private val selectPaletteModel: SelectPaletteModel) : BaseAdapter() {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view : RgbDiode
        if (convertView != null) {
            view = convertView as RgbDiode
            view.clearSubscriptions()
        } else {
            view = parent.inflate(R.layout.single_diode) as RgbDiode
            view.id = View.generateViewId()
        }

        view.colorStateList = createFrameModel.selectedPalette.colors.toMutableList()
        view.indexInMatrix = position
        val colorToDisplay = createFrameModel.displayedColors.get(position)
        view.setCurrentColor(colorToDisplay)
        // Subscribe after changing color
        view.subscribeOnColorChanged { saveViewColor(view) }
        view.invalidate()
        return view
    }

    private fun saveViewColor(view: RgbDiode) {
        createFrameModel.saveDiodeState(view.indexInMatrix, view.currentColor())
    }

    override fun getItem(position: Int): Any = position

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int = createFrameModel.displayedColors.size

    fun reset() {
        createFrameModel.reset()
        notifyDataSetChanged()
    }


}
