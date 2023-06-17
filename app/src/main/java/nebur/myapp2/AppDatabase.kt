package nebur.myapp2

import androidx.room.Database
import androidx.room.RoomDatabase
import nebur.data.source.local.dishes.DishEntity
import nebur.data.source.local.dishes.IdishLocal
import nebur.data.source.local.tegs.TegEntity

@Database(
    version = 1,
    entities = [
        DishEntity::class,
        TegEntity::class,
    ],
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun idishLocal(): IdishLocal
}