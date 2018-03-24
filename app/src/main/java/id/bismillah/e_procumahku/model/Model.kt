package id.bismillah.e_procumahku.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Created by Subkhan Sarif on 23/03/2018.
 */

// Basic model
@Entity(tableName = "user")
data class User(
        @PrimaryKey(autoGenerate = false)
        var id: String = "",
        var username: String? = null,
        var password: String? = null,
        var email: String? = null,
        var phone: String? = null,
        var instansi: String? = null,       // nama instansi
        var npwp: String? = null,           // nomer npwp
        var ppURL: String? = null,          // url profile picture
        var nomorRekening: String? = null,         // rekening number
        var userType: Int? = null,              // user type: 1: Model biasa, 2: Kontraktor
        var rating: Double? = null              // if user type is kontraktor, that it has star which shows rating from user
)

@Entity(
        tableName = "rekening",
        foreignKeys = [
            (ForeignKey(
                    entity = User::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("userId")
            ))
        ]
)
data class Rekening(
        var nama: String? = null,
        @PrimaryKey(autoGenerate = false)
        var nomorRekening: String = "",
        var saldo: Double = 0.0,
        var userId: String? = null
)

@Entity(
        tableName = "procurement",
        foreignKeys = [
            (ForeignKey(
                    entity = User::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("pic"))),
            (ForeignKey(
                    entity = Procurement::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("accepted")
            ))
        ]
)
class Procurement(
        @PrimaryKey(autoGenerate = false)
        var id: String,
        var title: String,              // judul
        var description: String,        // deskripsi singkat
        var alamat: String,             // alamat
        /**
         * foreign key user
         */
        var pic: String,                  // orang yang buat
        var created: Date,              // tanggal dibuat
        var dueDate: Date,              // tanggal deadline
        var layoutURL: String,          // url layout rumah
        var filePendukungURL: String,   // url file pendukung
        var lainlain: String,           // Notes dari user
        /**
         * foreign key procurement
         */
        var accepted: String,           // proposal id that is accepted by user
        var acceptedDate: Date          // tanggal diterima
)

@Entity(
        tableName = "proposal",
        foreignKeys = [
            (ForeignKey(
                    entity = Procurement::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("procurement")
            )),
            (ForeignKey(
                    entity = User::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("contractor")
            ))
        ]
)
data class Proposal(
        @PrimaryKey(autoGenerate = false)
        var id: String,
        var title: String,
        var description: String,
        /**
         * foreign key procurement
         */
        var procurement: String,       // corresponds to which procurement
        /**
         * Foreign key User
         */
        var contractor: String,               // creator of this proposal
        var proposalURL: String,            // url dokumen proposal
        var harga: Int,                         // proposed price
        var durasiPengerjaan: Int // prososed durasi pengerjaan dalam bulan
)

// Network Based model
class NetworkModel {
    data class LoginModel(
            var email: String,
            var password: String
    )

    data class SignupModel(
            var accNum: String? = null,
            var email: String? = null,
            var instansi: String? = null,
            var npwp: String? = null,
            var password: String? = null,
            var phone: String? = null,
            var ppURL: String? = null,
            var userType: Int = 0,
            var username: String? = null
    )

    data class CreateAccountModel(
            var amount: Double,
            var nama: String,
            var namaIbuKandung: String,
            var nik: Long,
            var tglLahir: String
    )
}