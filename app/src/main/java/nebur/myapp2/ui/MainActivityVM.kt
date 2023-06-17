package nebur.myapp2.ui

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import nebur.myapp2.R
import javax.inject.Inject

@HiltViewModel
class MainActivityVM @Inject constructor(
): ViewModel() {

    data class State(
        val currentGraphId: Int = nebur.menu.R.id.menu_graph
    )

    private val _s = MutableStateFlow(State())
    val s: StateFlow<State> = _s
    private fun setState(state: State.()-> State) {
        _s.value = _s.value.state()
    }

    val navListener: NavController.OnDestinationChangedListener =
        NavController.OnDestinationChangedListener { n, destination, _ ->
            getNearestGraph(destination)?.let {
                setState { copy(currentGraphId = it) }
            }
        }


    private fun getNearestGraph(destination: NavDestination): Int? {
        for (d in destination.hierarchy) {
            when(d.id) {
                nebur.menu.R.id.menu_graph -> return nebur.menu.R.id.menu_graph
                nebur.proj.profile.R.id.profile_graph -> return nebur.proj.profile.R.id.profile_graph
            }
        }
        return null
    }
}




















