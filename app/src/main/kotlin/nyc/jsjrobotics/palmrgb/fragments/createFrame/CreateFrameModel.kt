package nyc.jsjrobotics.palmrgb.fragments.createFrame

import android.content.Intent
import io.reactivex.disposables.Disposable
import nyc.jsjrobotics.palmrgb.Application
import nyc.jsjrobotics.palmrgb.dataStructures.Palette
import nyc.jsjrobotics.palmrgb.fragments.dialogs.selectPalette.SelectPaletteModel
import nyc.jsjrobotics.palmrgb.globalState.SavedPaletteModel
import nyc.jsjrobotics.palmrgb.service.PalmRgbBackground
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateFrameModel @Inject constructor(private val application: Application,
                                           private val savedPalettesModel: SavedPaletteModel,
                                           private val selectPaletteModel: SelectPaletteModel){
    var selectedPalette: Palette = savedPalettesModel.getStandardPalette()

    var displayedColors : MutableList<Int> = initialValues()

    fun diodeRange() : IntRange = IntRange(0, 63)
    fun saveDiodeState(index: Int, color: Int) {
        displayedColors[index] =  color
    }

    private val paletteChangedDisposable: Disposable = selectPaletteModel.onPaletteSelected
            .subscribe{ palette ->
                selectedPalette = palette
            }


    fun writeCurrentFrameToDatabase(frameTitle: String) {
        val data = ArrayList<Int>()
        data.addAll(displayedColors)
        val intent = Intent(application, PalmRgbBackground::class.java)
        intent.putExtra(PalmRgbBackground.EXTRA_FUNCTION, PalmRgbBackground.FUNCTION_SAVE_RGB_FRAME)
        intent.putIntegerArrayListExtra(PalmRgbBackground.EXTRA_RGB_MATRIX, data)
        intent.putExtra(PalmRgbBackground.EXTRA_TITLE, frameTitle)
        application.startService(intent)
    }

    fun reset() {
        displayedColors = initialValues()
    }

    private fun initialValues(): MutableList<Int>  = diodeRange().map { selectedPalette.colors.first() }.toMutableList()
}
