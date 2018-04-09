package nyc.jsjrobotics.palmrgb.fragments.createPalette

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.customViews.RgbDiode
import nyc.jsjrobotics.palmrgb.dataStructures.ColorOption
import nyc.jsjrobotics.palmrgb.inflate

class ColorOptionViewHolder(parent : ViewGroup) : RecyclerView.ViewHolder(inflateView(parent)) {
    private val rootXml: ViewGroup = itemView as ViewGroup
    private val rgbDiode: RgbDiode = itemView.findViewById(R.id.rgbDiode)
    private val colorName: TextView = itemView.findViewById(R.id.color_name)

    fun bind(colorOption: ColorOption, onClick: () -> Unit) {
        rgbDiode.setCurrentColor(colorOption.color)
        colorName.text = colorOption.title
        rgbDiode.isClickable = false
        rootXml.setOnClickListener { onClick.invoke() }
    }

    companion object {
        fun inflateView(parent: ViewGroup): View {
            return parent.inflate(R.layout.view_holder_color_option)
        }
    }
}
