package nyc.jsjrobotics.palmrgb.fragments.createPalette

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.dataStructures.ColorOption
import nyc.jsjrobotics.palmrgb.inflate
import javax.inject.Inject

class CreatePaletteView @Inject constructor(private val adapter : ColorOptionsAdapter){
    lateinit var rootXml: View
    private lateinit var optionsList : RecyclerView

    fun initView( container: ViewGroup, savedInstanceState: Bundle?) {
        rootXml = container.inflate(R.layout.fragment_create_palette)
        optionsList = rootXml.findViewById(R.id.color_options)
        optionsList.layoutManager = GridLayoutManager(rootXml.context,4)
        optionsList.adapter = adapter
        optionsList.isHorizontalScrollBarEnabled
    }

    fun onAddColor(): Observable<ColorOption> = adapter.onColorSelected

    fun setSavedColors(options: List<ColorOption>) {
        adapter.savedColors = options
    }

    fun setStandardColors(options: List<ColorOption>) {
        adapter.standardColors = options
    }


}
