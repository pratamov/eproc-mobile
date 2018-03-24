package id.bismillah.e_procumahku

import android.support.v4.app.Fragment

/**
 * Created by Subkhan Sarif on 22/03/2018.
 */
abstract class BaseFragment : Fragment() {
    fun hideActionBar() {
        (activity as BaseActivity).hideActionBar()
    }

    fun showActionBar() {
        (activity as BaseActivity).showActionBar()
    }

    fun changeTitle(title: String) {
        (activity as BaseActivity).changeTitle(title)
    }
}