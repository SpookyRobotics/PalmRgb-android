package nyc.jsjrobotics.palmrgb.fragments.createPalette

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import io.reactivex.Observable
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.dataStructures.ColorOption
import nyc.jsjrobotics.palmrgb.runOnMainThread
import javax.inject.Inject

class ChooseColorSubview @Inject constructor(private val adapter : ColorOptionsAdapter) {
    private lateinit var rootXml: View
    private lateinit var optionsList : RecyclerView

    fun init(xml : View) {
        rootXml = xml
        optionsList = rootXml.findViewById(R.id.color_options)
        optionsList.layoutManager = LinearLayoutManager(rootXml.context, RecyclerView.HORIZONTAL, false)
        optionsList.adapter = adapter
        optionsList.isHorizontalScrollBarEnabled
    }

    fun onAddColorSelected() : Observable<ColorOption> = adapter.onColorSelected

    fun refreshSavedColors() {
        adapter.invalideColorList()
    }
}
