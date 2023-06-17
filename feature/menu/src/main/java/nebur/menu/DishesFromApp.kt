package nebur.menu

import nebur.menu.model.DishesModel
import nebur.menu.model.Tegs

interface DishesFromApp {
    suspend fun getAll(teg: String): List<DishesModel>
    suspend fun getLastTeg(): Tegs
    suspend fun saveLastTeg(teg: Tegs)
}