package id.bismillah.e_procumahku.core.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import id.bismillah.e_procumahku.model.Procurement
import id.bismillah.e_procumahku.model.Proposal
import id.bismillah.e_procumahku.model.Rekening
import id.bismillah.e_procumahku.model.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * Created by Subkhan Sarif on 23/03/2018.
 */
@Database(entities = [(User::class), (Procurement::class), (Proposal::class), (Rekening::class)], version = 1)
@TypeConverters(Converters::class)
abstract class MyDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "id.bismillah.e_procumahku.database"
        private var instance: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            return instance ?: let {
                instance = Room.databaseBuilder(context.applicationContext, MyDatabase::class.java, DB_NAME)
//                        .fallbackToDestructiveMigration()
//                        .addMigrations(MigrationDB().migration1_2)
                        .build()
                return instance!!
            }
        }

        fun destroy() {
            instance?.close()
            instance = null
        }
    }

    abstract fun userDao(): UserDao
    abstract fun procurementDao(): ProcurementDao
    abstract fun proposalDao(): ProposalDao
    abstract fun rekeningDao(): RekeningDao
}

class UserDbProvider(context: Context) : UserDaoImpl {

    private val db = MyDatabase.getInstance(context)

    override fun insertOrUpdate(vararg user: User): Completable {
        return Completable.fromAction {
            db.userDao().insertOrUpdate(*user)
        }
    }

    override fun delete(vararg user: User): Completable {
        return Completable.fromAction {
            db.userDao().delete(*user)
        }
    }

    override fun getAll(): Flowable<List<User>> {
        return db.userDao().getAll()
    }

    override fun getById(id: String): Flowable<User> {
        return db.userDao().getById(id)
    }

    override fun maybeGetById(id: String): Maybe<User> {
        return db.userDao().maybeGetById(id)
    }
}

class RekeningDbProvider(context: Context) : RekeningDaoImpl {

    private val db = MyDatabase.getInstance(context)

    override fun insertOrUpdate(vararg rekening: Rekening): Completable {
        return Completable.fromAction {
            db.rekeningDao().insertOrUpdate(*rekening)
        }
    }

    override fun delete(vararg rekening: Rekening): Completable {
        return Completable.fromAction {
            db.rekeningDao().delete(*rekening)
        }
    }

    override fun getAll(): Flowable<List<Rekening>> {
        return db.rekeningDao().getAll()
    }

    override fun getById(id: String): Flowable<Rekening> {
        return db.rekeningDao().getById(id)
    }

    override fun maybeGetById(id: String): Maybe<Rekening> {
        return db.rekeningDao().maybeGetById(id)
    }
}

class ProcurementDbProvider(context: Context) : ProcurementDaoImpl {

    private val db = MyDatabase.getInstance(context)

    override fun insertOrUpdate(vararg procurement: Procurement): Completable {
        return Completable.fromAction {
            db.procurementDao().insertOrUpdate(*procurement)
        }
    }

    override fun delete(vararg procurement: Procurement): Completable {
        return Completable.fromAction {
            db.procurementDao().delete(*procurement)
        }
    }

    override fun getAll(): Flowable<List<Procurement>> {
        return db.procurementDao().getAll()
    }

    override fun getById(id: String): Flowable<Procurement> {
        return db.procurementDao().getById(id)
    }

    override fun maybeGetById(id: String): Maybe<Procurement> {
        return db.procurementDao().maybeGetById(id)
    }
}

class ProposalDbProvider(context: Context) : ProposalDaoImpl {

    private val db = MyDatabase.getInstance(context)

    override fun insertOrUpdate(vararg proposal: Proposal): Completable {
        return Completable.fromAction {
            db.proposalDao().insertOrUpdate(*proposal)
        }
    }

    override fun delete(vararg proposal: Proposal): Completable {
        return Completable.fromAction {
            db.proposalDao().delete(*proposal)
        }
    }

    override fun getAll(): Flowable<List<Proposal>> {
        return db.proposalDao().getAll()
    }

    override fun getById(id: String): Flowable<Proposal> {
        return db.proposalDao().getById(id)
    }

    override fun maybeGetById(id: String): Maybe<Proposal> {
        return db.proposalDao().maybeGetById(id)
    }
}