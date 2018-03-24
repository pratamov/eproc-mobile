package id.bismillah.e_procumahku

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Subkhan Sarif on 22/03/2018.
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActionBar()
    }

    fun showActionBar() {
        toolbar?.visibility = VISIBLE
    }

    fun hideActionBar() {
        toolbar?.visibility = GONE
    }

    fun changeTitle(title: String) {
        txt_toolbar_title.text = title
    }

    abstract fun setActionBar()
}