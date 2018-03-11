package nyc.jsjrobotics.palmrgb.createFrame

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.customViews.RgbDiode
import nyc.jsjrobotics.palmrgb.inflate

class RgbDiodeAdapter() : BaseAdapter() {
    val idMap: SparseArray<Int> = SparseArray()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view : RgbDiode
        if (convertView != null) {
            view = convertView as RgbDiode
        } else {
            view = parent.inflate(R.layout.single_diode) as RgbDiode
        }

        view.id = getItemId(position).toInt()
        return view

    }

    override fun getItem(position: Int): Any = position

    override fun getItemId(position: Int): Long {
        if (idMap.get(position) == null) {
            idMap.put(position, View.generateViewId())
        }
        return idMap.get(position).toLong()
    }

    override fun getCount(): Int = 64

}
