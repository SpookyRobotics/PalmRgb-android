package nyc.jsjrobotics.palmrgb.database

import android.arch.persistence.room.TypeConverter

class Converters {
    private val NON_STRING_LIST_SEPARATOR: String = "+"

    @TypeConverter
    fun fromRgbFrame(value: String): MutableRgbFrame {
        return MutableRgbFrame.fromDatabaseString(value)
    }

    @TypeConverter
    fun ingredientListToString(value: MutableRgbFrame): String {
        return MutableRgbFrame.toDatabaseString(value)
    }

    @TypeConverter
    fun fromIntegerList(value: String): List<Int> {
        return value.split(NON_STRING_LIST_SEPARATOR)
                .map { Integer.valueOf(it) }
    }


    @TypeConverter
    fun integerListToString(value: List<Int>): String {
        return value.joinToString(NON_STRING_LIST_SEPARATOR)
    }
}