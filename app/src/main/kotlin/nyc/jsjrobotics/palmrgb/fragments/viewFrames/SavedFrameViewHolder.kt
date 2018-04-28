package nyc.jsjrobotics.palmrgb.fragments.viewFrames

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import nyc.jsjrobotics.palmrgb.Application
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.customViews.XmlDiodeArray
import nyc.jsjrobotics.palmrgb.database.MutableRgbFrame
import nyc.jsjrobotics.palmrgb.inflate
import nyc.jsjrobotics.palmrgb.service.PalmRgbBackground
import javax.inject.Inject

class SavedFrameViewHolder(parent : ViewGroup) : RecyclerView.ViewHolder(createLayout(parent)) {

    @Inject
    lateinit var displayFrameAdapter: DisplayFrameAdapter

    @Inject
    lateinit var diodeArray: XmlDiodeArray

    private val rootXml = itemView
    private val title : TextView = rootXml.findViewById(R.id.frame_title)
    private val smallMatrix : ConstraintLayout = rootXml.findViewById(R.id.saved_rgb_matrix32)
    private val largeMatrix : ConstraintLayout = rootXml.findViewById(R.id.saved_rgb_matrix64)

    init {
        Application.inject(this)
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
        val view : ConstraintLayout
        if (rgbFrame.isLargeMatrix()) {
            view = largeMatrix
            smallMatrix.visibility = View.GONE
        } else {
            view = smallMatrix
            largeMatrix.visibility = View.GONE
        }
        diodeArray.setView(view)

        frameName = data.frameName
        title.text = frameName
        displayFrameAdapter.displayedColors = rgbFrame.colorList
        diodeArray.showColors(rgbFrame.colorList)
        rootXml.setOnClickListener { onSelectedListener() }
        diodeArray.setDiodesClickable(false)
        diodeArray.setOnClickListener { onSelectedListener() }


    }
}
