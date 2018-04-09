package nyc.jsjrobotics.palmrgb.fragments.createPalette

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.dataStructures.ColorOption
import nyc.jsjrobotics.palmrgb.database.AppDatabase
import nyc.jsjrobotics.palmrgb.executeInThread
import nyc.jsjrobotics.palmrgb.runOnMainThread
import javax.inject.Inject

class ColorOptionsAdapter @Inject constructor(val appDatabase: AppDatabase) : RecyclerView.Adapter<ColorOptionViewHolder>() {

    private var colorOptions : List<ColorOption> = emptyList()
    //private var loadedColors : Boolean = false
    private val colorSelected: PublishSubject<ColorOption> = PublishSubject.create()
    val onColorSelected: Observable<ColorOption> = colorSelected

    init {
        getColorOptions()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorOptionViewHolder {
        return ColorOptionViewHolder(parent)
    }

    override fun getItemCount(): Int = colorOptions.size

    override fun onBindViewHolder(holder: ColorOptionViewHolder, position: Int) {
        val colorOption = colorOptions[position]
        holder.bind(colorOption, {colorSelected.onNext(colorOption)})
    }

    private fun getColorOptions(postUpdate : () -> Unit = {}) {
        executeInThread {
            colorOptions = appDatabase.savedColorsDao().getAll().map { it.immutable() }
            postUpdate.invoke()
        }
    }

    fun invalideColorList() {
        getColorOptions({
            runOnMainThread {
                notifyDataSetChanged()

            }
        })
    }
}
