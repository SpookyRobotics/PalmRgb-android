package nyc.jsjrobotics.palmrgb.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface SavedColorsDao {
    @Query("SELECT * FROM ${AppDatabase.SAVED_COLORS_TABLE_NAME}")
    fun getAll() : List<MutableColorOption>

    @Query("SELECT * FROM ${AppDatabase.SAVED_COLORS_TABLE_NAME} WHERE ${AppDatabase.COLOR_NAME_COLUMN} IS :colorName")
    fun getColor(colorName : String) : MutableColorOption?

    @Delete
    fun delete(colorOption : MutableColorOption)

    @Insert
    fun insert(colorOption : MutableColorOption)
}
