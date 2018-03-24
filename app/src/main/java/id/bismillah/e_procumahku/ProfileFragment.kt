package id.bismillah.e_procumahku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.bismillah.e_procumahku.core.database.UserDbProvider
import id.bismillah.e_procumahku.core.manager.PrefManager
import id.bismillah.e_procumahku.extensions.blur
import id.bismillah.e_procumahku.extensions.stringResource
import id.bismillah.e_procumahku.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * Created by Subkhan Sarif on 21/03/2018.
 */
class ProfileFragment : BaseFragment() {

    lateinit var user: User
    lateinit var userDbProvider: UserDbProvider
    val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userDbProvider = UserDbProvider(context)

        val id = PrefManager.UserManager().loadUserId()
        id?.let {
            userDbProvider.getById(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        user = it
                        onUserResolved()
                    }?.also { disposable.add(it) }
        }

        changeTitle(context.stringResource(R.string.lbl_profile))
        hideActionBar()
        activity.blur(blur_view)
    }

    private fun onUserResolved() {
        txt_name.text = user.username
        txt_rek_number.text = user.nomorRekening
    }

    fun createVA(view: View) {

    }

    fun connectVA(view: View) {

    }

    fun createAccont(view: View) {

    }
}