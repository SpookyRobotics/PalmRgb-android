package nyc.jsjrobotics.palmrgb.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters


@Database(
        entities = arrayOf(MutableRgbFrame::class),
        version = 1
)

@TypeConverters(value = arrayOf(Converters::class))
abstract class AppDatabase : RoomDatabase() {

    companion object {
        val FILENAME: String = "PalmRgbAppDatabase.db"
    }

}