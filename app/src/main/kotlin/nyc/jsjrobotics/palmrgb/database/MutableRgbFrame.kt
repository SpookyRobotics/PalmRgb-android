package nyc.jsjrobotics.palmrgb.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "rgbFrames")
class MutableRgbFrame(
        var frameName : String = "",
        var colorList : List<Int> = emptyList()
) {
    @PrimaryKey(autoGenerate = true)
    var frameId : Long? = null

    companion object {
        val UNKNOWN_ID : Long = -1
        fun fromDatabaseString(value: String): MutableRgbFrame {
            return MutableRgbFrame()
        }

        fun toDatabaseString(value: MutableRgbFrame): String {
            return ""
        }
    }
}