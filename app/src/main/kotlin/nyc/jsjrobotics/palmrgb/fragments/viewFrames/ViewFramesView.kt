package nyc.jsjrobotics.palmrgb.fragments.viewFrames

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.database.MutableRgbFrame
import nyc.jsjrobotics.palmrgb.inflate
import javax.inject.Inject

class ViewFramesView @Inject constructor(val savedFramesAdapter : SavedFramesAdapter){
    lateinit var rootXml: View
    lateinit var savedFramesList : RecyclerView
    lateinit var noFramesMessage : TextView

    fun initView(container: ViewGroup, savedInstanceState: Bundle?) {
        rootXml = container.inflate(R.layout.fragment_view_frames)
        savedFramesList = rootXml.findViewById(R.id.saved_frames_list)
        noFramesMessage = rootXml.findViewById(R.id.no_frames_message)
        savedFramesList.layoutManager = LinearLayoutManager(container.context)
        savedFramesList.adapter = savedFramesAdapter
    }

    fun setData(frames: List<MutableRgbFrame>) {
        savedFramesAdapter.setData(frames)
        if (frames.isEmpty()) {
            noFramesMessage.visibility = View.VISIBLE
        } else {
            noFramesMessage.visibility = View.GONE
        }
    }

    fun onFrameSelected() = savedFramesAdapter.onFrameSelected

}
