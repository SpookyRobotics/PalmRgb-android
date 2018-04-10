package nyc.jsjrobotics.palmrgb.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = AppDatabase.SAVED_PALETTES_TABLE_NAME)
class MutablePalette (
        @PrimaryKey
        @ColumnInfo(name = AppDatabase.PALETTE_NAME_COLUMN)
        var name : String,
        @ColumnInfo(name = AppDatabase.PALETTE_VALUE_COLUMN)
        var colors: MutableList<Int>) {

    companion object {
        val UNNAMED = "UNAMED__MUTABLE_PALETTE"
    }

    constructor(vararg colors : Int) : this(UNNAMED, colors.toMutableList())
}
