package nebur.data.source.local.dishes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import nebur.data.source.local.tegs.TegEntity

@Dao
abstract class IdishLocal {

    @Query("SELECT * FROM dishes_table JOIN tegs_table ON dishes_table.id = tegs_table.dish_id "
            + "WHERE tegs_table.teg=:teg"
    )
    abstract suspend fun getAll(teg: String):Map<DishEntity, List<TegEntity>>

    @Query("DELETE FROM ${DishesEntityArg.TABLE}")
    abstract suspend fun deleteAll()

    @Insert
    abstract suspend fun insertDish(dishEntity: DishEntity): Long

    @Insert
    abstract suspend fun insertTeg(tegEntity: TegEntity)

    @Transaction
    open suspend fun insert(dishEntity: DishEntity, tegEntityes: List<TegEntity>) {
        val dishId = insertDish(dishEntity).toInt()
        tegEntityes.forEach {
            it.dishId = dishId
            insertTeg(it)
        }
    }
}