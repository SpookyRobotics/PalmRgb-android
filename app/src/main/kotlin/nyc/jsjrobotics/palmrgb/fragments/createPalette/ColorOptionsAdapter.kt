package nyc.jsjrobotics.palmrgb.fragments.createPalette

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.dataStructures.ColorOption
import javax.inject.Inject

class ColorOptionsAdapter @Inject constructor() : RecyclerView.Adapter<ColorOptionViewHolder>() {

    var colorOptions : List<ColorOption> = emptyList() ; private set(value) {
        field = value
        notifyDataSetChanged()
    }
    private val colorSelected: PublishSubject<ColorOption> = PublishSubject.create()
    val onColorSelected: Observable<ColorOption> = colorSelected
    var savedColors: List<ColorOption> = emptyList() ; set(value) {
        field = value
        val finalList = standardColors.toMutableList()
        finalList.addAll(value)
        colorOptions = finalList
    }

    var standardColors: List<ColorOption> = emptyList() ; set(value) {
        field = value
        val finalList = value.toMutableList()
        finalList.addAll(savedColors)
        colorOptions = finalList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorOptionViewHolder {
        return ColorOptionViewHolder(parent)
    }

    override fun getItemCount(): Int = colorOptions.size

    override fun onBindViewHolder(holder: ColorOptionViewHolder, position: Int) {
        val colorOption = colorOptions[position]
        holder.bind(colorOption, {colorSelected.onNext(colorOption)})
    }
}
