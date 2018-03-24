package id.bismillah.e_procumahku

import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.bismillah.e_procumahku.DashboardFragment.Companion.EXTRA_BUNDLE_DATA
import id.bismillah.e_procumahku.core.database.ProcurementDbProvider
import id.bismillah.e_procumahku.core.database.UserDbProvider
import id.bismillah.e_procumahku.model.Procurement
import id.bismillah.e_procumahku.model.Proposal
import id.bismillah.e_procumahku.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_propsal_detail.*

/**
 * Created by Subkhan Sarif on 23/03/2018.
 */
class ProposalDetailActivity : BaseActivity() {

    lateinit var proposal: Proposal
    lateinit var procurementDb: ProcurementDbProvider
    lateinit var userDb: UserDbProvider
    lateinit var user: User
    lateinit var procurement: Procurement
    val disposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_propsal_detail)

        procurementDb = ProcurementDbProvider(this)
        userDb = UserDbProvider(this)

        proposal = Gson().fromJson(intent.getStringExtra(EXTRA_BUNDLE_DATA), Proposal::class.java)
        procurementDb.getById(proposal.procurement)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    procurement = it
                    onProcurementLoaded()
                }.also { disposable.add(it) }
        userDb.getById(proposal.contractor)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    user = it
                    onUserLoaded()
                }.also { disposable.add(it) }
    }

    private fun onUserLoaded() {
        Picasso.get().load(user.ppURL).into(img_contractor)
    }

    private fun onProcurementLoaded() {

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    override fun setActionBar() {

    }

    fun call(view: View) {

    }

    fun sendEmail(view: View) {

    }

    fun sendMessage(view: View) {

    }

    fun makeDeal(view: View) {

    }
}