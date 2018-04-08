package nyc.jsjrobotics.palmrgb.dataStructures

import android.content.Intent
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.Application
import nyc.jsjrobotics.palmrgb.service.PalmRgbBackground
import nyc.jsjrobotics.palmrgb.service.PalmRgbBackground_MembersInjector
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SavedColorsModel @Inject constructor(){
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

}
