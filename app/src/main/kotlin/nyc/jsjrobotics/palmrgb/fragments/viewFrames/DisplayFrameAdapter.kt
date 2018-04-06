package nyc.jsjrobotics.palmrgb.fragments.viewFrames

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.customViews.RgbDiode
import nyc.jsjrobotics.palmrgb.inflate
import javax.inject.Inject

class DisplayFrameAdapter @Inject constructor() : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view : RgbDiode
        if (convertView != null) {
            view = convertView as RgbDiode
        } else {
            view = parent.inflate(R.layout.single_diode) as RgbDiode
            view.id = View.generateViewId()
            view.isClickable = false
        }

        view.indexInMatrix = position
        view.setCurrentColor(displayedColors[position])
        view.invalidate()
        return view
    }


    override fun getItem(position: Int): Any = displayedColors[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = displayedColors.size


    var displayedColors: List<Int> = emptyList() ; set(value) {
        field = value
        notifyDataSetChanged()
    }


}
