package gb.myhomework.nasa2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import gb.myhomework.nasa2.R
import kotlinx.android.synthetic.main.main_fragment.*

class ChipsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChipGroup()
    }

    private fun initChipGroup() {
        chipGroup.setOnCheckedChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position)?.let {
                when (it.id) {
                    R.id.chip_theme_light -> {
                        if (MainActivity.ThemeHolder.theme != R.style.Theme_NASA2) {
                            MainActivity.ThemeHolder.theme =
                                R.style.Theme_NASA2
                            requireActivity().recreate()
                        }
                    }
                    R.id.chip_theme_dark -> {
                        if (MainActivity.ThemeHolder.theme != R.style.Theme_NASA2_Dark) {
                            MainActivity.ThemeHolder.theme =
                                R.style.Theme_NASA2_Dark
                            requireActivity().recreate()
                        }
                    }
                    R.id.chip_theme_tropic -> {
                        if (MainActivity.ThemeHolder.theme != R.style.Theme_Tropic) {
                            MainActivity.ThemeHolder.theme =
                                R.style.Theme_Tropic
                            requireActivity().recreate()
                        }
                    }
                }
            }
        }
    }

}
