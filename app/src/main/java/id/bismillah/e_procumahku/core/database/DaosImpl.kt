package id.bismillah.e_procumahku.core.database

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
interface UserDaoImpl {
    fun insertOrUpdate(vararg user: User): Completable
    fun delete(vararg user: User): Completable
    fun getAll(): Flowable<List<User>>
    fun getById(id: String): Flowable<User>
    fun maybeGetById(id: String): Maybe<User>
}
interface RekeningDaoImpl {
    fun insertOrUpdate(vararg rekening: Rekening): Completable
    fun delete(vararg rekening: Rekening): Completable
    fun getAll(): Flowable<List<Rekening>>
    fun getById(id: String): Flowable<Rekening>
    fun maybeGetById(id: String): Maybe<Rekening>
}
interface ProcurementDaoImpl {
    fun insertOrUpdate(vararg procurement: Procurement): Completable
    fun delete(vararg procurement: Procurement): Completable
    fun getAll(): Flowable<List<Procurement>>
    fun getById(id: String): Flowable<Procurement>
    fun maybeGetById(id: String): Maybe<Procurement>
}
interface ProposalDaoImpl {
    fun insertOrUpdate(vararg proposal: Proposal): Completable
    fun delete(vararg proposal: Proposal): Completable
    fun getAll(): Flowable<List<Proposal>>
    fun getById(id: String): Flowable<Proposal>
    fun maybeGetById(id: String): Maybe<Proposal>
}