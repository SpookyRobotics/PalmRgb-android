package nyc.jsjrobotics.palmrgb.fragments.createFrame

import android.content.Intent
import nyc.jsjrobotics.palmrgb.Application
import nyc.jsjrobotics.palmrgb.dataStructures.MutablePalette
import nyc.jsjrobotics.palmrgb.service.PalmRgbBackground
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateFrameModel @Inject constructor(val application: Application){
    val displayedPalette: MutablePalette = MutablePalette(
            0xff000000.toInt(),
            0xffff9283.toInt(),
            0xff0070ff.toInt(),
            0xff08ff70.toInt(),
            0xff179033.toInt()
    )

    var displayedColors : MutableList<Int> = initialValues()

    fun diodeRange() : IntRange = IntRange(0, 63)
    fun saveDiodeState(index: Int, color: Int) {
        displayedColors[index] =  color
    }

    fun writeCurrentFrameToDatabase(frameTitle: String) {
        val data = ArrayList<Int>()
        data.addAll(displayedColors)
        val intent = Intent(application, PalmRgbBackground::class.java)
        intent.putIntegerArrayListExtra(PalmRgbBackground.EXTRA_RGB_MATRIX, data)
        intent.putExtra(PalmRgbBackground.EXTRA_TITLE, frameTitle)
        application.startService(intent)
    }

    fun reset() {
        displayedColors = initialValues()
    }

    private fun initialValues(): MutableList<Int>  = diodeRange().map { displayedPalette.colors.first() }.toMutableList()
}
