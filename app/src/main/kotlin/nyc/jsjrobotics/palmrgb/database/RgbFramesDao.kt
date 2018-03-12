package nyc.jsjrobotics.palmrgb.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface RgbFramesDao {
    @Query("SELECT * FROM ${AppDatabase.RGB_FRAMES_TABLE_NAME}")
    fun getAll() : List<MutableRgbFrame>

    @Delete
    fun delete(recipe : MutableRgbFrame)

    @Insert
    fun insert(recipe : MutableRgbFrame)
}
