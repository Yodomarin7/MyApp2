package nebur.data.repository

import nebur.data.source.local.dishes.DishEntity
import nebur.data.source.local.dishes.IdishLocal
import nebur.data.source.local.tegs.TegEntity
import nebur.data.source.remote.DishesRemote

class DishesREPO(
    private val dishesRemote: DishesRemote,
    private val idishLocal: IdishLocal
) {

    suspend fun getRemote(teg: String): List<DishesRemote.DishesModel>? {
        return dishesRemote.get(teg)
    }

    suspend fun getLocal(teg: String): Map<DishEntity, List<TegEntity>> {
        return idishLocal.getAll(teg)
    }

    suspend fun insert(dishEntity: DishEntity, tegEntityes: List<TegEntity>) {
        idishLocal.insert(dishEntity, tegEntityes)
    }

    suspend fun deleteLocal() {
        idishLocal.deleteAll()
    }
}