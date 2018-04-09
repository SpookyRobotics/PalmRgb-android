package nyc.jsjrobotics.palmrgb.dataStructures

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SavedPaletteModel @Inject constructor(private val savedColorsModel: SavedColorsModel){

    companion object {
        val STANDARD_COLORS = "STANDARD_COLORS"
    }
    fun getStandardPalette(): Palette {
        val colorValues = savedColorsModel.standardColors().map { it.color }
        return Palette(STANDARD_COLORS, colorValues)
    }
}