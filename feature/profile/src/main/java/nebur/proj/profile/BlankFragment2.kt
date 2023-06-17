package nebur.proj.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import nebur.proj.profile.databinding.FragmentBlank2Binding

class BlankFragment2 : Fragment(R.layout.fragment_blank2) {

    private lateinit var b: FragmentBlank2Binding
    private lateinit var n: NavController

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)
        if (context == null) return

        b = FragmentBlank2Binding.bind(v)
        n = findNavController()
    }

}