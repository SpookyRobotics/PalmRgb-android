package nyc.jsjrobotics.palmrgb

import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.dataStructures.Palette
import javax.inject.Inject
import javax.inject.Singleton

/***
 * A Repository of all palettes available to the current user. Starts off empty and must load
 * options from online and persistent storage
 */
@Singleton
class PaletteOptions @Inject constructor() {
    private val paletteList : MutableList<Palette> = mutableListOf()
    private val loadingComplete : PublishSubject<Boolean> = PublishSubject.create()
    private val onLoadingSuccessful = loadingComplete

    private val paletteListChanged : PublishSubject<Boolean> = PublishSubject.create()
    private val onPaletteListChanged = paletteListChanged

    fun currentPaletteList() : List<Palette> = paletteList

    fun load() {
        onLoadingSuccessful.onNext(false)
    }

    fun add(option : Palette) {
        paletteList.add(option)
        onPaletteListChanged.onNext(true)
    }

    fun remove(option : Palette) {
        if (paletteList.remove(option)) {
            onPaletteListChanged.onNext(true)
        }
    }
}
