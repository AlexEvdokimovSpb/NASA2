package gb.myhomework.nasa2.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class ViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {
    private val fragments = arrayOf(
        PictureOfTheDayFragment.newInstance(TODAY),
        PictureOfTheDayFragment.newInstance(YESTERDAY),
        PictureOfTheDayFragment.newInstance(DAY_BEFORE_YESTERDAY)
    )

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> fragments[TODAY]
            1 -> fragments[YESTERDAY]
            2 -> fragments[DAY_BEFORE_YESTERDAY]
            else -> throw IllegalStateException("Got $position, but there are only ${fragments.size}")
        }
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? =
        when (position) {
            TODAY -> "today"
            YESTERDAY -> "yesterday"
            DAY_BEFORE_YESTERDAY -> "day before yesterday"
            else -> throw IllegalStateException("Got $position, but there are only ${fragments.size}")
        }

    companion object {
        private val TODAY = 0
        private val YESTERDAY = 1
        private val DAY_BEFORE_YESTERDAY = 2
    }

}
