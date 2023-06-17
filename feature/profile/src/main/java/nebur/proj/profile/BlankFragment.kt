package nebur.proj.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import nebur.proj.profile.databinding.FragmentBlankBinding

class BlankFragment : Fragment(R.layout.fragment_blank) {

    private lateinit var b: FragmentBlankBinding
    private lateinit var n: NavController

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)
        if (context == null) return

        b = FragmentBlankBinding.bind(v)
        n = findNavController()

        b.button.setOnClickListener {
            n.navigate(R.id.action_blankFragment_to_blankFragment2)
        }
    }

}