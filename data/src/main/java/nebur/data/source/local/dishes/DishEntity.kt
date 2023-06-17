package nebur.data.source.local.dishes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

object DishesEntityArg {
    const val TABLE = "dishes_table"
    const val ID = "id"
    const val NAME = "name"
    const val DESC = "desc"
    const val PRICE = "price"
    const val IMAGE_URL = "image_url"
}

@Entity(
    tableName = DishesEntityArg.TABLE,
//    indices = [
//        Index(value = [BagEntityArg.PRODUCT_ID], unique = true),
//    ]
)
data class DishEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DishesEntityArg.ID)
    val id: Int,
    @ColumnInfo(name = DishesEntityArg.NAME) val name: String,
    @ColumnInfo(name = DishesEntityArg.DESC) val desc: String,
    @ColumnInfo(name = DishesEntityArg.PRICE) val price: Int,
    @ColumnInfo(name = DishesEntityArg.IMAGE_URL) val imageUrl: String,
)