package nyc.jsjrobotics.palmrgb.createFrame

import nyc.jsjrobotics.palmrgb.Application
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateFrameModel @Inject constructor(val application: Application){
    val colorStateList: MutableList<Int> = mutableListOf(
            0xffff9283.toInt(),
            0xff0070ff.toInt(),
            0xff08ff70.toInt(),
            0xff179033.toInt()
    )

    var displayedColors : MutableList<Int> = diodeRange().map { colorStateList.first() }.toMutableList()

    fun diodeRange() : IntRange = IntRange(0, 63)
    fun saveDiodeState(index: Int, color: Int) {
        displayedColors[index] =  color
    }
}
