package nyc.jsjrobotics.palmrgb.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = AppDatabase.SAVED_COLORS_TABLE_NAME)
data class MutableColorOption(
        @PrimaryKey
        @ColumnInfo(name = AppDatabase.COLOR_NAME_COLUMN)
        var title : String,
        @ColumnInfo(name = AppDatabase.COLOR_VALUE_COLUMN)
        var color : Int)