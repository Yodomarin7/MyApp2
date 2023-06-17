package nebur.data.source.local.tegs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import nebur.data.source.local.dishes.DishEntity
import nebur.data.source.local.dishes.DishesEntityArg

object TegsArg {
    const val TEGS_TABLE = "tegs_table"
    const val ID = "id"
    const val DISH_ID = "dish_id"
    const val TEG = "teg"
}

@Entity(
    tableName = TegsArg.TEGS_TABLE,
    foreignKeys = arrayOf(
        ForeignKey(
            entity = DishEntity::class,
            parentColumns = arrayOf(DishesEntityArg.ID),
            childColumns = arrayOf(TegsArg.DISH_ID),
            onDelete = ForeignKey.CASCADE
        ),
    )
)
data class TegEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TegsArg.ID)
    var id: Int,
    @ColumnInfo(name = TegsArg.DISH_ID) var dishId: Int,
    @ColumnInfo(name = TegsArg.TEG) val teg: String,
)