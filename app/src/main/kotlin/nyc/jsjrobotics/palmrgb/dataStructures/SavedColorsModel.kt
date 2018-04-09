package nyc.jsjrobotics.palmrgb.dataStructures

import android.content.Intent
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.Application
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.database.AppDatabase
import nyc.jsjrobotics.palmrgb.executeInThread
import nyc.jsjrobotics.palmrgb.runOnMainThread
import nyc.jsjrobotics.palmrgb.service.PalmRgbBackground
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SavedColorsModel @Inject constructor(val appDatabase: AppDatabase){
    private val colorsUpdated : PublishSubject<Boolean> = PublishSubject.create()
    val onColorsUpdated : Observable<Boolean> = colorsUpdated

    fun saveNewColor(colorName: String, colorToSave: Int) {
        val application = Application.instance()
        val intent = Intent(application, PalmRgbBackground::class.java)
        intent.putExtra(PalmRgbBackground.EXTRA_FUNCTION, PalmRgbBackground.FUNCTION_SAVE_COLOR)
        intent.putExtra(PalmRgbBackground.EXTRA_COLOR_NAME, colorName)
        intent.putExtra(PalmRgbBackground.EXTRA_COLOR_TO_SAVE, colorToSave)
        application.startService(intent)
    }

    fun loadSavedColors(): Single<List<ColorOption>> {
        return Single.create<List<ColorOption>> { emitter ->
            executeInThread {
                val colorOptions = appDatabase.savedColorsDao()
                        .getAll()
                        .map { it.immutable() }
                runOnMainThread {
                    emitter.onSuccess(colorOptions)
                }
            }
        }
    }

    fun loadStandardColors(): Single<List<ColorOption>> {
        return Single.create<List<ColorOption>> { emitter ->
            val context = Application.instance()
            val colorList = listOf(
                    Pair(R.string.red, R.color.red),
                    Pair(R.string.green, R.color.green),
                    Pair(R.string.blue, R.color.blue),
                    Pair(R.string.black, R.color.black),
                    Pair(R.string.yellow, R.color.yellow),
                    Pair(R.string.white, R.color.white)
            )
            val standardColors = colorList.map {
                ColorOption(
                        context.getString(it.first),
                        context.getColor(it.second)
                )
            }
            runOnMainThread {
                emitter.onSuccess(standardColors)
            }
        }
    }

}
