package id.bismillah.e_procumahku

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.squareup.picasso.Picasso
import id.bismillah.e_procumahku.core.database.RekeningDbProvider
import id.bismillah.e_procumahku.core.database.UserDbProvider
import id.bismillah.e_procumahku.core.manager.PrefManager
import id.bismillah.e_procumahku.extensions.ToastShort
import id.bismillah.e_procumahku.extensions.stringResource
import id.bismillah.e_procumahku.model.NetworkModel
import id.bismillah.e_procumahku.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_account_creation.*

/**
 * Created by Subkhan Sarif on 24/03/2018.
 */
class RekeningAccountCreationActivity : BaseActivity() {
    lateinit var app: EProcApplication
    lateinit var user: User
    lateinit var userDbProvider: UserDbProvider
    lateinit var rekeningDbProvider: RekeningDbProvider
    private var isAccountShowCreation = false
    var disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as EProcApplication
        setContentView(R.layout.activity_account_creation)

        userDbProvider = UserDbProvider(this)
        rekeningDbProvider = RekeningDbProvider(this)

        hideAccountCreationField()

        if (EProcApplication.user == null) {
            val userid = PrefManager.UserManager().loadUserId()
            userid?.let {
                userDbProvider.getById(it)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            user = it
                            onUserResolved()
                        }
            }
        } else {
            user = EProcApplication.user!!
            onUserResolved()
        }
    }

    private fun onUserResolved() {
        user.ppURL?.let {
            Picasso.get()
                    .load(it)
                    .into(img_user)
        }
        txt_username.text = user.username ?: ""
        val currentString = btn_connect_existing_account.text.toString()
        if (user.nomorRekening == null) {
            btn_connect_existing_account.visibility = GONE
        } else {
            btn_connect_existing_account.text = String.format(currentString + "\n" + stringResource(R.string.placeholder_rek_number), user.nomorRekening)
            btn_connect_existing_account.visibility = VISIBLE
        }
    }

    fun createNewAccount(view: View) {
        showAccountCreationField()
    }

    fun doCreate(view: View) {
        if (txt_amount.text.isEmpty()
                || txt_ibu_kandung.text.isEmpty()
                || txt_nik.text.isEmpty()
                || txt_tgl_lahir.text.isEmpty()) {
            Toast.makeText(this, stringResource(R.string.lbl_please_fill_field), Toast.LENGTH_SHORT).show()
            return
        }
        val amount = if (txt_amount.text.toString().isEmpty()) 0.0 else txt_amount.text.toString().toDouble()
        val name = if (user.username == null) "" else user.username!!
        val ibu = txt_ibu_kandung.text.toString()
        val nik = txt_nik.text.toString().toLong()
        val tglLahir = txt_tgl_lahir.text.toString()
        val model = NetworkModel.CreateAccountModel(amount, name, ibu, nik, tglLahir)
        app.server.createNewRekening(user.id, model)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    user.nomorRekening = it.nomorRekening
                    userDbProvider.insertOrUpdate(user)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe()
                            .dispose()
                    rekeningDbProvider.insertOrUpdate(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe()
                            .dispose()
                    goToMainActivity()
                }, {
                    ToastShort(stringResource(R.string.lbl_something_went_wrong))
                }, {}
                ).also { disposable.add(it) }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainUserActivity::class.java)
        startActivity(intent)
    }

    private fun showAccountCreationField() {
        ll_container.visibility = GONE
        view_triangle.visibility = GONE
        card_view.visibility = GONE
        ll_create_account_container.visibility = VISIBLE
        isAccountShowCreation = true
    }

    private fun hideAccountCreationField() {
        ll_container.visibility = VISIBLE
        view_triangle.visibility = VISIBLE
        card_view.visibility = VISIBLE
        ll_create_account_container.visibility = GONE
        isAccountShowCreation = false
    }

    override fun onBackPressed() {
        if (isAccountShowCreation) {
            hideAccountCreationField()
        } else {
            super.onBackPressed()
        }
    }

    fun useExistingAccount(view: View) {

    }

    override fun setActionBar() {

    }
}