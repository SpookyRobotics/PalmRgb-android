package nyc.jsjrobotics.palmrgb.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import nyc.jsjrobotics.palmrgb.dataStructures.RgbFrame
import nyc.jsjrobotics.palmrgb.service.PalmRgbBackground


@Entity(tableName = AppDatabase.RGB_FRAMES_TABLE_NAME)
data class MutableRgbFrame(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = AppDatabase.FRAME_ID_COLUMN)
        var frameId : Long? = null,
        @ColumnInfo(name = AppDatabase.FRAME_NAME_COLUMN)
        var frameName : String = "",
        @ColumnInfo(name = AppDatabase.COLOR_LIST_COLUMN)
        var colorList : List<Int> = emptyList(),
        @ColumnInfo(name = AppDatabase.MATRIX_TYPE_COLUMN)
        var matrixType : Int = UNKNOWN_MATRIX
) {


    fun isLargeMatrix(): Boolean {
        return matrixType == PalmRgbBackground.LARGE_MATRIX_TYPE
    }

    fun immutable(): RgbFrame {
        return RgbFrame(frameId, frameName, colorList, matrixType)
    }
    companion object {
        val UNKNOWN_ID : Long = -1
        val UNKNOWN_MATRIX : Int = -1
        fun fromDatabaseString(value: String): MutableRgbFrame {
            return MutableRgbFrame()
        }

        fun toDatabaseString(value: MutableRgbFrame): String {
            return ""
        }
    }
}