package gb.myhomework.nasa2.ui

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import gb.myhomework.nasa2.R

class ViewPagerAdapter(fragmentManager: FragmentManager, private val context: Context) :
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

    override fun getPageTitle(position: Int): CharSequence? {
        val charSequence: CharSequence? = when (position) {
            TODAY -> context.getString(R.string.today)
            YESTERDAY -> context.getString(R.string.yesterday)
            DAY_BEFORE_YESTERDAY -> context.getString(R.string.day_before_yesterday)
            else -> throw IllegalStateException("Got $position, but there are only ${fragments.size}")
        }
        return charSequence
    }

    companion object {
        private val TODAY = 0
        private val YESTERDAY = 1
        private val DAY_BEFORE_YESTERDAY = 2
    }

}
