package id.bismillah.e_procumahku

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import id.bismillah.e_procumahku.handler.BottomNavigationHandler
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Subkhan Sarif on 21/03/2018.
 */
class MainUserActivity : BaseActivity() {
    lateinit var bottomNavHandler: BottomNavigationHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigation: View = findViewById(R.id.bottom_navigation_container)
        bottomNavHandler = BottomNavigationHandler(this, bottomNavigation)
        bottomNavHandler.setActive(0).also { changeFragmentTo(it) }
    }

    override fun setActionBar() {
        setSupportActionBar(toolbar)
    }

    fun goToDashboard(view: View) {
        bottomNavHandler.setActive(0).also { changeFragmentTo(it) }
    }

    fun goToProcurement(view: View) {
        bottomNavHandler.setActive(1).also { changeFragmentTo(it) }
    }

    fun goToProfile(view: View) {
        bottomNavHandler.setActive(2).also { changeFragmentTo(it) }
    }

    private fun changeFragmentTo(f: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, f)
                .commit()
    }
}