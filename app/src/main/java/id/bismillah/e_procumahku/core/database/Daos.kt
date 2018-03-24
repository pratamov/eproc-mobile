package id.bismillah.e_procumahku.core.database

import android.arch.persistence.room.*
import id.bismillah.e_procumahku.model.Procurement
import id.bismillah.e_procumahku.model.Proposal
import id.bismillah.e_procumahku.model.Rekening
import id.bismillah.e_procumahku.model.User
import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * Created by Subkhan Sarif on 23/03/2018.
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(vararg user: User)

    @Delete
    fun delete(vararg user: User)

    @Query("select * from user")
    fun getAll(): Flowable<List<User>>

    @Query("select * from user where id = :id")
    fun getById(id: String): Flowable<User>

    @Query("select * from user where id = :id")
    fun maybeGetById(id: String): Maybe<User>
}

@Dao
interface RekeningDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(vararg rekening: Rekening)

    @Delete
    fun delete(vararg rekening: Rekening)

    @Query("select * from rekening")
    fun getAll(): Flowable<List<Rekening>>

    @Query("select * from rekening where nomorRekening = :id")
    fun getById(id: String): Flowable<Rekening>

    @Query("select * from rekening where nomorRekening = :id")
    fun maybeGetById(id: String): Maybe<Rekening>
}

@Dao
interface ProcurementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(vararg procurement: Procurement)

    @Delete
    fun delete(vararg procurement: Procurement)

    @Query("select * from procurement")
    fun getAll(): Flowable<List<Procurement>>

    @Query("select * from procurement where id = :id")
    fun getById(id: String): Flowable<Procurement>

    @Query("select * from procurement where id = :id")
    fun maybeGetById(id: String): Maybe<Procurement>
}

@Dao
interface ProposalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(vararg proposal: Proposal)

    @Delete
    fun delete(vararg proposal: Proposal)

    @Query("select * from proposal")
    fun getAll(): Flowable<List<Proposal>>

    @Query("select * from proposal where id = :id")
    fun getById(id: String): Flowable<Proposal>

    @Query("select * from proposal where id = :id")
    fun maybeGetById(id: String): Maybe<Proposal>
}