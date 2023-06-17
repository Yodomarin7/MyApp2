package nebur.menu.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nebur.menu.DishesFromApp
import nebur.menu.model.DishesModel
import nebur.menu.model.Tegs
import javax.inject.Inject

@HiltViewModel
class MenuFrgVM @Inject constructor(
    private val dishesFromApp: DishesFromApp,
) : ViewModel()  {

    sealed interface Page {
        object Wait: Page
        object Dishes: Page
    }

    data class State(
        val p: Page = Page.Wait,
        val items: List<DishesModel>? = null,
        val lastTeg: Tegs = Tegs.ALL,
    )

    private val _s = MutableStateFlow(State())
    val s: StateFlow<State> = _s
    private fun setState(state: State.()-> State) {
        _s.value = _s.value.state()
    }

    init {
        viewModelScope.launch {
            val teg = dishesFromApp.getLastTeg()
            getDishes(teg)
            setState { copy(lastTeg = teg) }
        }
    }

    private var dishesJob: Job? = null
    fun getDishes(tegs: Tegs) {
        dishesJob?.cancel()
        setState { copy(p = Page.Wait, items = listOf()) }
        dishesJob = viewModelScope.launch {
            dishesFromApp.saveLastTeg(tegs)
            setState { copy(lastTeg = tegs) }
            delay(5)
            val dishes = dishesFromApp.getAll(tegs.str)
            setState { copy(p = Page.Dishes, items = dishes) }
        }
    }

}





















