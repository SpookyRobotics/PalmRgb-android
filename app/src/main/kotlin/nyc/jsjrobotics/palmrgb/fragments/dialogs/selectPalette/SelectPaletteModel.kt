package nyc.jsjrobotics.palmrgb.fragments.dialogs.selectPalette

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.dataStructures.Palette
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SelectPaletteModel @Inject constructor() {
    private val paletteSelected : PublishSubject<Palette> = PublishSubject.create()
    val onPaletteSelected : Observable<Palette> = paletteSelected

    fun selectPalette(palette: Palette) {
        paletteSelected.onNext(palette)
    }


}
