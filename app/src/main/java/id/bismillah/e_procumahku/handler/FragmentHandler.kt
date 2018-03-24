package id.bismillah.e_procumahku.handler

import android.content.Context
import id.bismillah.e_procumahku.DashboardFragment
import id.bismillah.e_procumahku.ProcurementFragment
import id.bismillah.e_procumahku.ProfileFragment

/**
 * Created by Subkhan Sarif on 21/03/2018.
 */
class FragmentHandler(var context: Context) {
    val fDashboard = DashboardFragment()
    val fProcurement = ProcurementFragment()
    val fProfile = ProfileFragment()
}