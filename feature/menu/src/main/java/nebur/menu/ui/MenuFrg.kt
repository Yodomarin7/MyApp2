package nebur.menu.ui

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import nebur.menu.R
import nebur.menu.databinding.FrgMenuBinding
import nebur.menu.model.Tegs
import kotlin.math.roundToInt

@AndroidEntryPoint
class MenuFrg : Fragment(R.layout.frg_menu) {

    private val vm: MenuFrgVM by hiltNavGraphViewModels(R.id.frgMenu)
    private lateinit var b: FrgMenuBinding
    private lateinit var n: NavController

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)
        if (context == null) return

        b = FrgMenuBinding.bind(v)
        n = findNavController()

        val density = Resources.getSystem().displayMetrics.density
        val px = (density * 112).roundToInt()*(-1)

        b.appbar.addOnOffsetChangedListener { _, verticalOffset ->

            if(verticalOffset == px) {
                b.shadow.visibility = VISIBLE
            }
            else {
                b.shadow.visibility = GONE
            }
        }

        b.chipAll.setOnClickListener { vm.getDishes(Tegs.ALL) }
        b.chipSalad.setOnClickListener { vm.getDishes(Tegs.SALAD) }
        b.chipRice.setOnClickListener { vm.getDishes(Tegs.RICE) }
        b.chipFish.setOnClickListener { vm.getDishes(Tegs.FISH) }

        b.rv.layoutManager = LinearLayoutManager(requireContext())
        val adapter = PizzasRvAdapter()
        b.rv.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.s.collect { s ->
                    setContainer(s.p)
                    setChip(s.lastTeg)
                    s.items?.let { adapter.submitList(s.items) }
                }
            }
        }
    }

    private fun setChip(tegs: Tegs) {
        when(tegs) {
            Tegs.ALL -> b.chipAll.isChecked = true
            Tegs.SALAD -> b.chipSalad.isChecked = true
            Tegs.RICE -> b.chipRice.isChecked = true
            Tegs.FISH -> b.chipFish.isChecked = true
        }
    }

    private fun setContainer(p: MenuFrgVM.Page) {
        b.rv.visibility = View.GONE
        b.errorP.visibility = View.GONE
        b.waitP.visibility = View.GONE

        when(p) {
            MenuFrgVM.Page.Dishes -> b.rv.visibility = View.VISIBLE
            MenuFrgVM.Page.Wait -> b.waitP.visibility = View.VISIBLE
        }
    }

}











