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
        const val FILENAME: String = "PalmRgbAppDatabase.db"
        const val RGB_FRAMES_TABLE_NAME = "rgbFrames"
        const val FRAME_NAME_COLUMN = "frame_name"
        const val COLOR_LIST_COLUMN = "color_list"
        const val FRAME_ID_COLUMN = "frame_id"
    }

    abstract fun rgbFramesDao(): RgbFramesDao

}