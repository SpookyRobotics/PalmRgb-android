package nyc.jsjrobotics.palmrgb.fragments.createPalette

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.dataStructures.ColorOption
import nyc.jsjrobotics.palmrgb.dataStructures.Palette
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreatePaletteModel @Inject constructor() {
    private val newPaletteColors : MutableList<ColorOption> = mutableListOf()
    private val newPaletteColorsModified : PublishSubject<MutableList<ColorOption>> = PublishSubject.create()
    val onNewPaletteColorsModified : Observable<MutableList<ColorOption>> = newPaletteColorsModified

    val requstSavePalette : PublishSubject<Palette> = PublishSubject.create()
    val onRequstSavePalette : Observable<Palette> = requstSavePalette

    fun addColorOption(colorSelected: ColorOption) {
        newPaletteColors.add(colorSelected)
        newPaletteColorsModified.onNext(newPaletteColors)
    }

    fun requestSavePalette(title: String) {
        requstSavePalette.onNext(Palette(title, newPaletteColors.map { it.color }))
    }

}
