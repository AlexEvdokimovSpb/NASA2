package gb.myhomework.nasa2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gb.myhomework.nasa2.R

class MainActivity : AppCompatActivity() {

//    private val ui: ActivityMainBinding by lazy {
//        ActivityMainBinding.inflate(layoutInflater)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(ui.root)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commit()
        }
    }
}