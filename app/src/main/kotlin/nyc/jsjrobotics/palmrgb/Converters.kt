package nyc.jsjrobotics.palmrgb

import android.arch.persistence.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromRgbFrame(value: String): MutableRgbFrame {
        return MutableRgbFrame.fromDatabaseString(value)
    }

    @TypeConverter
    fun ingredientListToString(value: MutableRgbFrame): String {
        return MutableRgbFrame.toDatabaseString(value)
    }
}