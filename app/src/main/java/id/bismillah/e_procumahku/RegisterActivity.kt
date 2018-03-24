package id.bismillah.e_procumahku

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import id.bismillah.e_procumahku.core.database.UserDbProvider
import id.bismillah.e_procumahku.core.manager.PrefManager
import id.bismillah.e_procumahku.model.NetworkModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_register.*

/**
 * Created by Subkhan Sarif on 24/03/2018.
 */
class RegisterActivity : BaseActivity() {

    lateinit var userDbProvider: UserDbProvider
    val disposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userDbProvider = UserDbProvider(this)

    }

    fun register(view: View) {
        val app = application as EProcApplication
        val signupModel = NetworkModel.SignupModel()
                .apply {
                    username = txt_username.text.toString()
                    email = txt_email.text.toString()
                    password = txt_password.text.toString()
                    phone = txt_phone.text.toString()
                    npwp = txt_npwp.text.toString()
                }
        app.server.signup(signupModel)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        {
                            Log.d("register", "response: $it")

                            it.let {
                                PrefManager.UserManager().saveUser(it)
                                EProcApplication.user = it
                                userDbProvider.insertOrUpdate(it)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe()
                            }.also {
                                disposable.add(it)
                            }

                        },
                        // onError
                        {
                            Log.e("Register", "error: ${it.message}")
                            it.printStackTrace()
                        },
                        // onComplete
                        {
                            val intent = Intent(this, RekeningAccountCreationActivity::class.java)
                            startActivity(intent)
                        }
                )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    override fun setActionBar() {

    }
}