package nyc.jsjrobotics.palmrgb.fragments.viewFrames

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import nyc.jsjrobotics.palmrgb.Application
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.customViews.XmlDiodeArray64
import nyc.jsjrobotics.palmrgb.database.MutableRgbFrame
import nyc.jsjrobotics.palmrgb.inflate
import javax.inject.Inject

class SavedFrameViewHolder(parent : ViewGroup) : RecyclerView.ViewHolder(createLayout(parent)) {

    @Inject
    lateinit var displayFrameAdapter: DisplayFrameAdapter

    @Inject
    lateinit var diodeArray: XmlDiodeArray64

    private val rootXml = itemView
    private val title : TextView = rootXml.findViewById(R.id.frame_title)
    private val rgbMatrix : ConstraintLayout = rootXml.findViewById(R.id.saved_rgb_matrix)

    init {
        Application.inject(this)
        diodeArray.setView(rgbMatrix)
    }

    private lateinit var data: MutableRgbFrame
    companion object {
        fun createLayout(parent: ViewGroup) : View {
            return parent.inflate(R.layout.view_holder_saved_frame)
        }
    }
    private var frameName : String? = null


    fun bind(rgbFrame: MutableRgbFrame, onSelectedListener: () -> Unit) {
        data = rgbFrame
        frameName = data.frameName
        title.text = frameName
        displayFrameAdapter.displayedColors = rgbFrame.colorList
        diodeArray.setDiodeColors(rgbFrame.colorList)
        rootXml.setOnClickListener { onSelectedListener() }
        diodeArray.setDiodesClickable(false)
        diodeArray.setOnClickListener { onSelectedListener() }


    }
}
