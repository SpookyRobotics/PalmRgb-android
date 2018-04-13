package nyc.jsjrobotics.palmrgb.fragments.createPalette

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.dataStructures.ColorOption
import javax.inject.Inject

class ColorOptionsAdapter @Inject constructor() : RecyclerView.Adapter<ColorOptionViewHolder>() {
    private var displayRemoveIcon = false

    var colorOptions : List<ColorOption> = emptyList() ; set(value) {
        field = value
        notifyColorsChanged()
        notifyDataSetChanged()
    }

    private fun notifyColorsChanged() {
        colorsChanged.onNext(colorOptions)
    }

    private val colorSelected: PublishSubject<ColorOption> = PublishSubject.create()
    val onColorSelected: Observable<ColorOption> = colorSelected

    private val removeColorRequest: PublishSubject<Int> = PublishSubject.create()
    val onRemoveColorRequest: Observable<Int> = removeColorRequest

    private val colorsChanged: PublishSubject<List<ColorOption>> = PublishSubject.create()
    val onColorsChanged: Observable<List<ColorOption>> = colorsChanged

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorOptionViewHolder {
        return ColorOptionViewHolder(parent, displayRemoveIcon)
    }

    override fun getItemCount(): Int = colorOptions.size

    override fun onBindViewHolder(holder: ColorOptionViewHolder, position: Int) {
        val colorOption = colorOptions[position]
        holder.bind(colorOption,
                {colorSelected.onNext(colorOption)},
                {
                    removeColorRequest.onNext(position)
                }
        )
    }

    fun addColor(colorSelected: ColorOption) {
        val finalList = colorOptions.toMutableList()
        finalList.add(colorSelected)
        colorOptions = finalList
    }

    fun enableRemoveOption() {
        displayRemoveIcon = true
        notifyDataSetChanged()
    }

    fun removeOption(colorOption: Int) {
        val finalList = colorOptions.toMutableList()
        finalList.removeAt(colorOption)
        colorOptions = finalList
    }
}
