package nyc.jsjrobotics.palmrgb.fragments.createPalette

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.dataStructures.ColorOption
import nyc.jsjrobotics.palmrgb.inflate
import nyc.jsjrobotics.palmrgb.runOnMainThread
import javax.inject.Inject

class CreatePaletteView @Inject constructor(private val colorOptionsAdapter : ColorOptionsAdapter,
                                            private val newPaletteAdapter : ColorOptionsAdapter){
    lateinit var rootXml: View
    private lateinit var optionsList : RecyclerView
    private lateinit var newPalette : RecyclerView
    private lateinit var createPaletteButton: Button
    private val createPaletteSelected: PublishSubject<Boolean> = PublishSubject.create()
    val onCreatePaletteSelected: Observable<Boolean> = createPaletteSelected

    var savedColors: List<ColorOption> = emptyList() ; set(value) {
        field = value
        setColorOptions()
    }

    var standardColors: List<ColorOption> = emptyList() ; set(value) {
        field = value
        setColorOptions()
    }

    fun initView( container: ViewGroup, savedInstanceState: Bundle?) {
        rootXml = container.inflate(R.layout.fragment_create_palette)
        optionsList = rootXml.findViewById(R.id.color_options)
        newPalette = rootXml.findViewById(R.id.palette_colors)
        createPaletteButton = rootXml.findViewById(R.id.create_palette)
        createPaletteButton.setOnClickListener { createPaletteSelected.onNext(true) }
        optionsList.layoutManager = GridLayoutManager(rootXml.context,4)
        optionsList.adapter = colorOptionsAdapter

        newPalette.layoutManager = LinearLayoutManager(rootXml.context, RecyclerView.HORIZONTAL, false)
        newPalette.adapter = newPaletteAdapter
        newPaletteAdapter.enableRemoveOption()
        updateCreatePaletteButton()

    }

    fun updateCreatePaletteButton() {
        val isVisible: Boolean = !newPaletteAdapter.colorOptions.isEmpty()
        val visibility = if (isVisible) View.VISIBLE else View.GONE
        createPaletteButton.visibility = visibility
    }

    fun onAddColor(): Observable<ColorOption> = colorOptionsAdapter.onColorSelected
    fun onRemoveColorRequest() : Observable<Int> = newPaletteAdapter.onRemoveColorRequest

    private fun setColorOptions() {
        val finalList = standardColors.toMutableList()
        finalList.addAll(savedColors)
        colorOptionsAdapter.colorOptions = finalList
    }

    fun addPaletteColor(colorSelected: ColorOption) {
        newPaletteAdapter.addColor(colorSelected)
        newPalette.scrollToPosition(newPaletteAdapter.colorOptions.size -1)

    }

    fun getCreatePaletteColors() = newPaletteAdapter.colorOptions
    fun removeColor(colorOption: Int) {
        newPaletteAdapter.removeOption(colorOption)
        updateCreatePaletteButton()
    }
}
