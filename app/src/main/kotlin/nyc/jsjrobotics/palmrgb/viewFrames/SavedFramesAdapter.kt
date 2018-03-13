package nyc.jsjrobotics.palmrgb.viewFrames

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import nyc.jsjrobotics.palmrgb.database.MutableRgbFrame
import javax.inject.Inject

class SavedFramesAdapter @Inject constructor(): RecyclerView.Adapter<SavedFrameView>(){

    private var data: List<MutableRgbFrame> = emptyList()

    fun setData(rgbFrames : List<MutableRgbFrame>) {
        data = rgbFrames
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SavedFrameView {
        return SavedFrameView(parent!!)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: SavedFrameView, position: Int) {
        holder.bind(data[position])
    }

}
