package nebur.myapp2.impl.forMenu

import kotlinx.coroutines.flow.firstOrNull
import nebur.data.repository.AllUserSaveREPO
import nebur.data.repository.DishesREPO
import nebur.data.source.local.dishes.DishEntity
import nebur.data.source.local.tegs.TegEntity
import nebur.menu.DishesFromApp
import nebur.menu.model.DishesModel
import nebur.menu.model.Tegs
import javax.inject.Inject

class DishesForMenu @Inject constructor(
    private val dishesREPO: DishesREPO,
    private val allUserSaveREPO: AllUserSaveREPO
): DishesFromApp {

    override suspend fun getAll(teg: String): List<DishesModel> {
        val remoteList = dishesREPO.getRemote(teg)

        val result = mutableListOf<DishesModel>()

        if(remoteList == null) {
            val localList = dishesREPO.getLocal(teg)

            localList.forEach { map->
                val dish = map.key
                result.add(
                    DishesModel(
                        id = dish.id,
                        name = dish.name,
                        price = dish.price,
                        description = dish.desc,
                        image_url = dish.imageUrl,
                        tegs = getTegsFromEntity(map.value)
                    )
                )
            }
        }
        else {
            dishesREPO.deleteLocal()
            remoteList.forEach {
                result.add(
                    DishesModel(
                        id = it.id,
                        name = it.name,
                        price = it.price,
                        description = it.description,
                        image_url = it.image_url,
                        tegs = getTegsFromString(it.tegs)
                    )
                )

                val tegs = mutableListOf<TegEntity>()
                it.tegs.forEach {
                    tegs.add(TegEntity(id = 0, dishId = 0, teg = it))
                }
                dishesREPO.insert(
                    DishEntity(id = 0, name = it.name, desc = it.description, price = it.price,
                        imageUrl = it.image_url), tegs
                )
            }
        }

        return result
    }

    override suspend fun getLastTeg(): Tegs {
        return when(allUserSaveREPO.getLastTeg().firstOrNull()) {
            "Все меню" -> Tegs.ALL
            "Салаты" -> Tegs.SALAD
            "С рисом" -> Tegs.RICE
            "С рыбой" -> Tegs.FISH
            else -> Tegs.ALL
        }
    }

    override suspend fun saveLastTeg(teg: Tegs) {
        val str = when(teg) {
            Tegs.ALL -> "Все меню"
            Tegs.SALAD -> "Салаты"
            Tegs.RICE -> "С рисом"
            Tegs.FISH -> "С рыбой"
        }
        allUserSaveREPO.saveLocality(str)
    }

    private fun getTegsFromEntity(entityList: List<TegEntity>): List<Tegs> {
        val tegsList = mutableListOf<Tegs>()

        entityList.forEach { ent->
            val teg = when(ent.teg) {
                "Все меню" -> Tegs.ALL
                "Салаты" -> Tegs.SALAD
                "С рисом" -> Tegs.RICE
                "С рыбой" -> Tegs.FISH
                else -> Tegs.ALL
            }
            tegsList.add(teg)
        }

        return tegsList
    }

    private fun getTegsFromString(strList: List<String>): List<Tegs> {
        val tegsList = mutableListOf<Tegs>()

        strList.forEach { str->
            val teg = when(str) {
                "Все меню" -> Tegs.ALL
                "Салаты" -> Tegs.SALAD
                "С рисом" -> Tegs.RICE
                "С рыбой" -> Tegs.FISH
                else -> Tegs.ALL
            }
            tegsList.add(teg)
        }

        return tegsList
    }

}