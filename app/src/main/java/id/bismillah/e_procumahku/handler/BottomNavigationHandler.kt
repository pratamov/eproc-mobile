package id.bismillah.e_procumahku.handler

import android.content.Context
import android.graphics.PorterDuff
import android.support.v4.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import id.bismillah.e_procumahku.R
import id.bismillah.e_procumahku.extensions.colorResource

/**
 * Created by Subkhan Sarif on 21/03/2018.
 */
class BottomNavigationHandler(private var context: Context, private var view: View) {

    private val llDashboard = view.findViewById<LinearLayout>(R.id.btn_dashboard)
    private val llProcurement = view.findViewById<LinearLayout>(R.id.btn_procurement)
    private val llProfile = view.findViewById<LinearLayout>(R.id.btn_profile)
    private val fragmentHandler: FragmentHandler = FragmentHandler(context)

    fun setActive(page: Int, active: Boolean = true): Fragment {
        val fragment: Fragment
        val ll: LinearLayout
        if (page == 1) {
            if (active) {
                setActive(0, false)
                setActive(2, false)
            }
            fragment = fragmentHandler.fProcurement
            ll = llProcurement
        } else if (page == 2) {
            if (active) {
                setActive(0, false)
                setActive(1, false)
            }
            fragment = fragmentHandler.fProfile
            ll = llProfile
        } else {
            if (active) {
                setActive(1, false)
                setActive(2, false)
            }
            fragment = fragmentHandler.fDashboard
            ll = llDashboard
        }

        val image = ll.getChildAt(0) as ImageView?
        val label = ll.getChildAt(1) as TextView?

        if (active) {
            image?.drawable?.setColorFilter(context.colorResource(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP)
            label?.setTextColor(context.colorResource(R.color.colorAccent))
        } else {
            image?.drawable?.setColorFilter(context.colorResource(R.color.white), PorterDuff.Mode.SRC_ATOP)
            label?.setTextColor(context.colorResource(R.color.white))
        }
        return fragment
    }
}