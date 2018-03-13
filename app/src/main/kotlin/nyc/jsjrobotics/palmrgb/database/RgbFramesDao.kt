package nyc.jsjrobotics.palmrgb.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface RgbFramesDao {
    @Query("SELECT * FROM ${AppDatabase.RGB_FRAMES_TABLE_NAME}")
    fun getAll() : List<MutableRgbFrame>

    @Query("SELECT * FROM ${AppDatabase.RGB_FRAMES_TABLE_NAME} WHERE ${AppDatabase.FRAME_ID_COLUMN} IS :frameId")
    fun getFrame(frameId : Long) : MutableRgbFrame?

    @Delete
    fun delete(frame : MutableRgbFrame)

    @Insert
    fun insert(recipe : MutableRgbFrame)
}
