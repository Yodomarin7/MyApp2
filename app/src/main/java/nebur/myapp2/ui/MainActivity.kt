package nebur.myapp2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import nebur.myapp2.R
import nebur.myapp2.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private lateinit var n: NavController
    private val vm: MainActivityVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        n = navHostFragment.navController

        n.addOnDestinationChangedListener(vm.navListener)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.s.collect { s ->
                    updateUi(s)
                }
            }
        }

        b.btnMenu.setOnClickListener {
            navigateTo(nebur.menu.R.id.menu_graph, vm.s.value.currentGraphId)
        }

        b.btnProfile.setOnClickListener {
            navigateTo(nebur.proj.profile.R.id.profile_graph, vm.s.value.currentGraphId)
        }

        b.btnBag.setOnClickListener {
//            navigateTo(nebur.bag.R.id.bag_graph, vm.s.value.currentGraphId)
        }
    }

    private fun updateUi(s: MainActivityVM.State) {
        updateBottomNav(s.currentGraphId)
    }

    private fun updateBottomNav(graph: Int) {
        val colorEnable = ContextCompat.getColor(this, nebur.common.R.color._FD3A69)
        val colorDisable = ContextCompat.getColor(this, nebur.common.R.color._7B7B7B)

        b.icnMenu.setColorFilter(colorDisable)
        b.txtMenu.setTextColor(colorDisable)
        b.icnProfile.setColorFilter(colorDisable)
        b.txtProfile.setTextColor(colorDisable)
        b.icnBag.setColorFilter(colorDisable)
        b.txtBag.setTextColor(colorDisable)

        when(graph) {
            nebur.menu.R.id.menu_graph -> {
                b.icnMenu.setColorFilter(colorEnable)
                b.txtMenu.setTextColor(colorEnable)
            }
            nebur.proj.profile.R.id.profile_graph -> {
                b.icnProfile.setColorFilter(colorEnable)
                b.txtProfile.setTextColor(colorEnable)
            }
        }
    }

    private fun navigateTo(destinationId: Int, currentGraphId: Int) {
        if (destinationId == currentGraphId) {
            when(destinationId) {
                nebur.menu.R.id.menu_graph -> {
//                n.popBackStack(n.graph.findStartDestination().id, false)
                    n.popBackStack(nebur.menu.R.id.frgMenu, false)
                }
                nebur.proj.profile.R.id.profile_graph -> {
                    n.popBackStack(nebur.proj.profile.R.id.blankFragment, false)
                }
                else -> {
                    n.navigate(destinationId, null, navOptions {
                        launchSingleTop = true
                        popUpTo(n.graph.findStartDestination().id)
                    })
                }
            }
        }
        else {
            n.navigate(destinationId, null, navOptions {
                launchSingleTop = true
                restoreState = true
                popUpTo(n.graph.findStartDestination().id) {
                    saveState = true
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        n.removeOnDestinationChangedListener(vm.navListener)
    }
}