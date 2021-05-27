package gb.myhomework.nasa2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gb.myhomework.nasa2.R
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalStateException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(ThemeHolder.theme)
        setContentView(R.layout.activity_main)
        initBottomNavigationView()
    }

    private fun initBottomNavigationView() {
        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_day -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, MainFragment())
                        .commit()
                }
                R.id.bottom_view_fav -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, MotionFragment())
                        .commitAllowingStateLoss()
                }
                R.id.bottom_view_settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, SettingFragment())
                        .commit()
                }
                else -> {
                    throw IllegalStateException("Got $item!")
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
        bottom_navigation_view.selectedItemId = R.id.bottom_view_day
    }

    object ThemeHolder {
        var theme = R.style.Theme_NASA2
    }

}