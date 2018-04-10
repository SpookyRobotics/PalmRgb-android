package nyc.jsjrobotics.palmrgb.fragments.createPalette

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.dataStructures.ColorOption
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreatePaletteModel @Inject constructor() {
    private val newPaletteColors : MutableList<ColorOption> = mutableListOf()
    private val colorsModified : PublishSubject<MutableList<ColorOption>> = PublishSubject.create()
    val onColorsModified : Observable<MutableList<ColorOption>> = colorsModified

    fun addColorOption(colorSelected: ColorOption) {
        newPaletteColors.add(colorSelected)
    }

}
