package nyc.jsjrobotics.palmrgb.createFrame

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.customViews.RgbDiode
import nyc.jsjrobotics.palmrgb.inflate
import javax.inject.Inject

class RgbDiodeAdapter @Inject constructor(val createFrameModel : CreateFrameModel) : BaseAdapter() {
    private var resetting: Boolean = false

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view : RgbDiode
        if (convertView != null) {
            view = convertView as RgbDiode
            if (!resetting) {
                createFrameModel.saveDiodeState(view.indexInMatrix, view.currentColor())
            }
        } else {
            view = parent.inflate(R.layout.single_diode) as RgbDiode
            view.id = View.generateViewId()
            view.colorStateList = createFrameModel.colorStateList
        }

        view.indexInMatrix = position
        val lastColor = createFrameModel.displayedColors.get(position)
        view.setCurrentColor(lastColor)
        view.invalidate()
        if (position == createFrameModel.diodeRange().last) {
            resetting = false
        }
        return view

    }

    override fun getItem(position: Int): Any = position

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int = createFrameModel.displayedColors.size

    fun reset() {
        resetting = true
        createFrameModel.reset()
        notifyDataSetChanged()
    }


}
