package id.bismillah.e_procumahku.core.manager

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import id.bismillah.e_procumahku.model.User

/**
 * Created by Subkhan Sarif on 26/02/2018.
 */
class PrefManager {
    companion object {
        private val PREFERENCE_NAME = "id.bluebox.planetsportactive.core.manager.PREFERENCE_MANAGER"
        private var preference: SharedPreferences? = null

        fun init(context: Context) {
            preference = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        }

    }

    class UserManager {
        private val PREF_NAME_USER_ID = "user_id"
        private val PREF_NAME_USER_EMAIL = "user_email"

        fun saveUser(user: User) {
            preference?.edit()?.apply {
                putString(PREF_NAME_USER_ID, user.id)
                putString(PREF_NAME_USER_EMAIL, user.email)
            }?.apply()
        }

        fun loadUserId(): String? {
            return preference?.getString(PREF_NAME_USER_ID, null)
        }

        fun loadUserEmail(): String? {
            return preference?.getString(PREF_NAME_USER_EMAIL, null)
        }

        fun clearCurrentTrainingSession() {
            preference?.edit()?.apply {
                remove(PREF_NAME_USER_ID)
                remove(PREF_NAME_USER_EMAIL)
            }?.apply()
        }
    }
}