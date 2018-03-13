package nyc.jsjrobotics.palmrgb.viewFrames

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import nyc.jsjrobotics.palmrgb.Application
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.database.MutableRgbFrame
import nyc.jsjrobotics.palmrgb.inflate
import javax.inject.Inject

class SavedFrameView(parent : ViewGroup) : RecyclerView.ViewHolder(createLayout(parent)) {

    init {
        Application.inject(this)
    }

    @Inject
    lateinit var displayFrameAdapter: DisplayFrameAdapter

    private val rootXml = itemView
    private val title : TextView = rootXml.findViewById(R.id.frame_title)
    private val rgbGrid : GridView = rootXml.findViewById(R.id.saved_rgb_matrix)
    private lateinit var data: MutableRgbFrame

    companion object {
        fun createLayout(parent: ViewGroup) : View {
            return parent.inflate(R.layout.view_holder_saved_frame)
        }
    }


    fun bind(rgbFrame: MutableRgbFrame) {
        data = rgbFrame
        title.text = data.frameName
        displayFrameAdapter.displayedColors = rgbFrame.colorList
        rgbGrid.adapter = displayFrameAdapter
    }
}
