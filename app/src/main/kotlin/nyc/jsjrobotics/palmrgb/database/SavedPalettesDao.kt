package nyc.jsjrobotics.palmrgb.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface SavedPalettesDao {
    @Query("SELECT * FROM ${AppDatabase.SAVED_PALETTES_TABLE_NAME}")
    fun getAll() : List<MutablePalette>

    @Query("SELECT * FROM ${AppDatabase.SAVED_PALETTES_TABLE_NAME}")
    fun getAllFlowable() : Flowable<List<MutablePalette>>

    @Query("SELECT * FROM ${AppDatabase.SAVED_PALETTES_TABLE_NAME} WHERE ${AppDatabase.COLOR_NAME_COLUMN} IS :paletteName")
    fun getColor(paletteName : String) : MutableColorOption?

    @Delete
    fun delete(paletteName : MutablePalette)

    @Insert
    fun insert(paletteName : MutablePalette)
}
