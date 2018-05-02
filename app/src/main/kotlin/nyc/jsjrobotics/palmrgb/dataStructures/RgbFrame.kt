package nyc.jsjrobotics.palmrgb.dataStructures

import nyc.jsjrobotics.palmrgb.database.MutableRgbFrame
import nyc.jsjrobotics.palmrgb.service.PalmRgbBackground


class RgbFrame(
        val frameId: Long?,
        val frameName : String,
        val colorList : List<Int>,
        val matrixType : Int
) {
    fun isLargeMatrix(): Boolean {
        return matrixType == PalmRgbBackground.LARGE_MATRIX_TYPE
    }

    fun isSmallMatrix(): Boolean {
        return matrixType == PalmRgbBackground.SMALL_MATRIX_TYPE
    }

    fun mutable() : MutableRgbFrame {
        return MutableRgbFrame(frameId, frameName, colorList, matrixType)
    }
}