package nyc.jsjrobotics.palmrgb.globalState

import android.content.Intent
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.Application
import nyc.jsjrobotics.palmrgb.dataStructures.Palette
import nyc.jsjrobotics.palmrgb.database.AppDatabase
import nyc.jsjrobotics.palmrgb.database.MutablePalette
import nyc.jsjrobotics.palmrgb.executeInThread
import nyc.jsjrobotics.palmrgb.runOnMainThread
import nyc.jsjrobotics.palmrgb.service.PalmRgbBackground
import javax.inject.Inject
import javax.inject.Singleton

/***
 * A Repository of all palettes available to the current user. Starts off empty and must load
 * options from online and persistent storage
 */

@Singleton
class SavedPaletteModel @Inject constructor(private val application: Application,
                                            private val savedColorsModel: SavedColorsModel,
                                            private val appDatabase: AppDatabase){

    companion object {
        val STANDARD_COLORS = "STANDARD_COLORS"
    }

    private val paletteList : MutableList<Palette> = mutableListOf()
    private val loadingComplete : PublishSubject<Boolean> = PublishSubject.create()
    private val onLoadingSuccessful = loadingComplete

    private val paletteListChanged : PublishSubject<Boolean> = PublishSubject.create()
    val onPaletteListChanged = paletteListChanged

    private var paletteUpdateDisposable: Disposable

    init {
        paletteUpdateDisposable = appDatabase.savedPalettesDao().getAllFlowable().subscribe { paletteOptions ->
            setPaletteList(paletteOptions)
        }
    }

    fun loadPaletteList() : Single<List<Palette>> {
        val result : Single<List<Palette>> = Single.create { emitter ->
            executeInThread {
                val currentPalettes = appDatabase.savedPalettesDao().getAll()
                setPaletteList(currentPalettes)
                runOnMainThread {
                    emitter.onSuccess(paletteList.toList())
                }
            }
        }
        return result
    }

    private fun setPaletteList(paletteOptions: List<MutablePalette>) {
        paletteList.clear()
        paletteList.addAll(paletteOptions.map { it.immutable() })
        paletteListChanged.onNext(true)
    }

    fun loadedPaletteList() = paletteList.toList()

    fun getStandardPalette(): Palette {
        val colorValues = savedColorsModel.standardColors().map { it.color }
        return Palette(STANDARD_COLORS, colorValues)
    }

    fun savePalette(palette: Palette) {
        val intent = Intent(application, PalmRgbBackground::class.java)
        intent.putExtra(PalmRgbBackground.EXTRA_FUNCTION, PalmRgbBackground.FUNCTION_SAVE_PALETTE)
        intent.putExtra(PalmRgbBackground.EXTRA_PALETTE_NAME, palette.name)
        intent.putExtra(PalmRgbBackground.EXTRA_PALETTE_COLORS_TO_SAVE, palette.colors.toIntArray())
        application.startService(intent)
    }
}