package nyc.jsjrobotics.palmrgb.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import nyc.jsjrobotics.palmrgb.dataStructures.Palette

@Entity(tableName = AppDatabase.SAVED_PALETTES_TABLE_NAME)
class MutablePalette (
        @PrimaryKey
        @ColumnInfo(name = AppDatabase.PALETTE_NAME_COLUMN)
        var name : String,
        @ColumnInfo(name = AppDatabase.PALETTE_VALUE_COLUMN)
        var colors: MutableList<Int>) {

    fun immutable(): Palette = Palette(name, colors)

    companion object {
        val UNNAMED = "UNAMED__MUTABLE_PALETTE"
    }

    constructor(vararg colors : Int) : this(UNNAMED, colors.toMutableList())
}
