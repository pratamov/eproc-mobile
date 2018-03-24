package id.bismillah.e_procumahku

/**
 * Created by Subkhan Sarif on 22/03/2018.
 */
class ProcurementModel {
    var title: String = "Procurement"
    var description: String = "Description"

    fun generate(): List<ProcurementModel> {
        return arrayListOf(ProcurementModel(), ProcurementModel(), ProcurementModel(), ProcurementModel())
    }
}