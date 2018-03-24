package id.bismillah.e_procumahku

import android.app.Application
import id.bismillah.e_procumahku.core.database.UserDbProvider
import id.bismillah.e_procumahku.core.manager.PrefManager
import id.bismillah.e_procumahku.core.network.Connection
import id.bismillah.e_procumahku.core.network.Server
import id.bismillah.e_procumahku.model.User

/**
 * Created by Subkhan Sarif on 23/03/2018.
 */
class EProcApplication : Application() {
    companion object {
        var user: User? = null
    }

    lateinit var server: Server
    override fun onCreate() {
        super.onCreate()
        server = Connection().init()
        PrefManager.init(this)

    }
}