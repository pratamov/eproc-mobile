package id.bismillah.e_procumahku

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import id.bismillah.e_procumahku.core.database.UserDbProvider
import id.bismillah.e_procumahku.core.manager.PrefManager
import id.bismillah.e_procumahku.extensions.ToastShort
import id.bismillah.e_procumahku.extensions.stringResource
import id.bismillah.e_procumahku.model.NetworkModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var userDbProvider: UserDbProvider
    lateinit var app: EProcApplication
    val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as EProcApplication

        setContentView(R.layout.activity_login)

        userDbProvider = UserDbProvider(this)

        val userDbProvider = UserDbProvider(this)
        val id = PrefManager.UserManager().loadUserId()
//        id?.let {
//            userDbProvider.maybeGetById(it)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe {
//                        EProcApplication.user = it
//                        if (EProcApplication.user != null) {
//                            goToMainActivity()
//                        }
//                    }
//        }
    }

    fun login(view: View) {
        if (txt_email.text.toString().isEmpty() || txt_password.text.toString().isEmpty()) {
            Toast.makeText(this, stringResource(R.string.lbl_email_password_cannot_empty), Toast.LENGTH_SHORT).show()
            return
        }
        val loginModel = NetworkModel.LoginModel(txt_email.text.toString(), txt_password.text.toString())
        app.server.login(loginModel.email, loginModel.password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        {
                            it.let {
                                PrefManager.UserManager().saveUser(it)
                                EProcApplication.user = it
                                userDbProvider.insertOrUpdate(it)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe()
                            }.also {
                                disposable.add(it)
                                val intent = Intent(this, RekeningAccountCreationActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        },
                        // onError
                        {
                            Log.d("Login", "Error: ${it.message}")
                            it.printStackTrace()
                            ToastShort(stringResource(R.string.lbl_login_error))
                        },
                        // onComplete
                        {
                            if (EProcApplication.user == null) {
                                ToastShort(stringResource(R.string.lbl_something_went_wrong))
                            }
                        }
                )
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainUserActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    fun register(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}
